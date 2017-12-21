package com.staed.stores;

import java.util.HashMap;
import java.util.Set;

public class EventType {
    private static HashMap<String, Integer> evts = new HashMap<>();

    public static void populate(HashMap<String, Integer> map) {
        evts = map;
    }

    public Integer getValue(String key) {
        if (evts.keySet().contains(key))
            return evts.get(key);
        else if (evts.keySet().contains("Other"))
            return evts.get("Other");
        else
            return 0;
    }
    
    public Set<String> getKeys() {
    	return evts.keySet();
    }
}

// public enum EventType {
//     UniversityCourse,
//     Seminars,
//     CertificationPreparation,
//     Certification,
//     TechnicalTraining,
//     Other
// };