package com.staed.services;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.Period;
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
import com.staed.dao.InfoDAO;
import com.staed.dao.NoteDAO;
import com.staed.dao.RequestDAO;

import stores.FieldValueWrapper;

public class RequestService extends Service {
	private static RequestDAO reqDAO;
	private Request request;
	
	private static AttachmentDAO attachDAO;
	private static NoteDAO noteDAO;
	private static InfoDAO infoDAO;
	
	public RequestService() {
		reqDAO = new RequestDAO();
		request = null;
		
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
	 * Add a reimbursement to the table as well as all it's related data
	 * @param Request
	 * @param Info
	 * @param List&lt;Attachment&gt;
	 * @param List&lt;Note&gt;
	 * @return Boolean indicating success or failure
	 */
	public boolean add(Request request, Info info, List<Attachment> files, List<Note> notes) {
		reqDAO.addRequest(request);
		int requestId = reqDAO.getAddedRequestId(request);

		info.setRequestId(requestId);
		infoDAO.addInfo(info);

		for (Attachment file : files) {
			file.setRequestId(requestId);
			attachDAO.addAttachment(file);
		}

		for (Note note : notes) {
			note.setRequestId(requestId);
			noteDAO.addNote(note);
		}

		return false;
	}
	
	/**
	 * Modify the specificed fields of a Reimbursement request with the passed
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
	
}