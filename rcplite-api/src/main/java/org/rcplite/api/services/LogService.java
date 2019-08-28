package org.rcplite.api.services;

import org.rcplite.api.logging.Log;

public interface LogService {
	public void log(Log log);
	public void log(String logMessage);
}
