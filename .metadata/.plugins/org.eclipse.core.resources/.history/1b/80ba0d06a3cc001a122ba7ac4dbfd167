package blockChain;

import org.json.JSONObject;

public class JSONObjectSingleton {

	private static JSONObject uniqueJSONObject = null;

	private JSONObjectSingleton() {

	}

	public static JSONObject getUniqueJSONObject(Object obj) {

		if (uniqueJSONObject == null) {
			uniqueJSONObject = new JSONObject(obj);
		}

		return uniqueJSONObject;
	}
}
