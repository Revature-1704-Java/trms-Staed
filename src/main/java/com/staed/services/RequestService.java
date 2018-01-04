package com.staed.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.staed.beans.Attachment;
import com.staed.beans.Note;
import com.staed.beans.Request;
import com.staed.beans.Info;
import com.staed.dao.AttachmentDAO;
import com.staed.dao.EventTypeDAO;
import com.staed.dao.FormatDAO;
import com.staed.dao.InfoDAO;
import com.staed.dao.NoteDAO;
import com.staed.dao.RequestDAO;
import com.staed.stores.ColumnNames;
import com.staed.stores.FieldValueWrapper;

/**
 * A Service that handles Requests and all the objects associated with them.
 * It handles the messy details of actually providing the services required.
 */
public class RequestService extends Service {
	private static ColumnNames names;
	private static final int rejectPower = 4;
	
	private static RequestDAO reqDAO;
	private Request request;
	
	private static EventTypeDAO typeDAO;
	private static FormatDAO formatDAO;
	
	private static AttachmentDAO attachDAO;
	private static NoteDAO noteDAO;
	private static InfoDAO infoDAO;
	
	public RequestService() {
		reqDAO = new RequestDAO();
		request = null;
		
		typeDAO = new EventTypeDAO();
		formatDAO = new FormatDAO();
		
		attachDAO = new AttachmentDAO();
		noteDAO = new NoteDAO();
		infoDAO = new InfoDAO();
	}
	
	@Override
	public void close() {
		reqDAO.close();
		
		attachDAO.close();
		noteDAO.close();
		infoDAO.close();
	}
	
	/**
	 * Displays all the reimbursements the current user is permitted to see
	 * @param String - Email of the current user
	 * @return A List of reimbursements
	 */
	public List<Request> displayAll(String email) {
		return reqDAO.getAllRequest(email);
	}
	
	/**
	 * Displays a specific reimbursement if they have permission to see it
	 * @param int - The Request's Id
	 * @param email - The current user's email
	 * @return A Json containing that reimbursement and all the other 
	 * information related to it
	 */
	public String displayOne(int id, String email) {
		request = reqDAO.getRequest(id, email);
		int rid = request.getId();
		
		Gson gson = new Gson();
		String req = gson.toJson(request);
		String info = gson.toJson(infoDAO.getInfo(rid));
		String notes = gson.toJson(noteDAO.getNotes(rid));
		String attachments = gson.toJson(attachDAO.getAttachments(rid));
		
		return mergeJson(req, info, notes, attachments);
	}

	/**
	 * Adds a reimbursement to the table with it's related data
	 * @param Request
	 * @param Info
	 * @param List&lt;Attachment&gt;
	 * @return Boolean indicating success or failure
	 */
	public boolean add(Request request, Info info, List<Attachment> files) {
		reqDAO.addRequest(request);
		int requestId = reqDAO.getAddedRequestId(request);
		if (requestId == 0)
			return false;

		// Try to add Info, clean up if it fails
		info.setRequestId(requestId);
		if (infoDAO.addInfo(info) == 0) {
			reqDAO.deleteRequest(requestId);
			return false;
		}

		for (Attachment file : files) {
			file.setRequestId(requestId);
			if (attachDAO.addAttachment(file) == 0)
				return false;
		}

		return true;
	}

	/**
	 * Add notes related to an entry in the Reimbursement table
	 * @param int - The id of the request
	 * @param List&lt;Note&gt;
	 * @return Boolean indicating success or failure
	 */
	public boolean addNotes(int requestId, List<Note> notes) {
		for (Note note : notes) {
			note.setRequestId(requestId);
			if (noteDAO.addNote(note) == 0)
				return false;
		}

		return true;
	}
	
	/**
	 * Modify the specified fields of a Reimbursement request with the passed
	 * in field values
	 * @param int - The Id of the request being modified
	 * @param List&lt;FieldValueWrapper&gt; - A list containing wrappers that
	 * hold the name, value, and type of that field.
	 */
	public boolean modifyRequest(int id, List<FieldValueWrapper> fields) {
		return reqDAO.updateRequest(id, fields) > 0 ? true : false;
	}
	
	/**
	 * Merges the Json representations of tables
	 * @param ...String - Varargs of Json strings
	 * @return The merged Json
	 */
	private String mergeJson(String ...jsons) {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		
		Collection<Map<String, Object>> jsonMaps = new ArrayList<>();
		for (String json : jsons) {
			jsonMaps.add(gson.fromJson(json, type));
		}
		
		Map<String, Object> prev = new HashMap<>();
		Iterator<Map<String, Object>> jsonIter = jsonMaps.iterator();
		while (jsonIter.hasNext()) {
			Map<String, Object> cur = jsonIter.next();
			
			prev = Stream.of(prev, cur)
				.map(Map::entrySet)
				.flatMap(Set::stream)
				.collect(Collectors.toMap(
						Entry::getKey,
						Entry::getValue,
						(oldEntry, newEntry) -> { 
							return oldEntry; 
						}
				));
		}
		
		return gson.toJson(prev);
	}
	
	/**
	 * Checks if the request was created by one of the employees in the list
	 * @param Request - The Request
	 * @param relatedEmployees - A list of employees possibly related to the
	 * request
	 * @return Whether they have permission over the request
	 */
	public boolean checkPermission(Request req, List<String> relatedEmployees) {
		if (req != null && relatedEmployees.contains(req.getEmail())) {
			return true;
		} else
			return false;
	}
	
	/**
	 * Checks if the request was created by one of the employees in the list
	 * If it is and the Power level passed in is higher than the current state
	 * of the request, approve the Request to that Power level
	 * @param requestId - The id of the request in question
	 * @param powerLevel - The power level to attempt to approve to
	 * @param relatedEmployees - A list of employees who are possibly related
	 * to this request
	 * @return The post-operation power level of the request, 0 if the request
	 * doesn't exist
	 */
	public int approve(int requestId, int powerLevel, List<String> relatedEmployees) {
		Request req = reqDAO.getRequest(requestId);
		
		if (!checkPermission(req, relatedEmployees))
			return 0;
		else if (req.getState() < powerLevel) {
			FieldValueWrapper fvw = new FieldValueWrapper(names.state, powerLevel);
			List<FieldValueWrapper> list = new ArrayList<>();
			list.add(fvw);
				
			if (modifyRequest(requestId, list))
				return powerLevel;
		}
		
		return req.getState();
	}
	
	public boolean reject(int requestId, int powerLevel, List<String> relatedEmployees) {
		Request req = reqDAO.getRequest(requestId);
		
		if (!checkPermission(req, relatedEmployees))
			return false;
		else if (req.getState() < powerLevel) {
			FieldValueWrapper fvw = new FieldValueWrapper(names.state, rejectPower);
			List<FieldValueWrapper> list = new ArrayList<>();
			list.add(fvw);
			
			return modifyRequest(requestId, list);
		}
		return false;
	}
	
	public int gradingFormatNameToId(String name) {
		return formatDAO.idFromName(name);
	}
	
	public int eventTypeNameToId(String name) {
		return typeDAO.idFromName(name);
	}
}