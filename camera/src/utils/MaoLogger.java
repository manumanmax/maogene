package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.Start;

public abstract class MaoLogger implements Logger {
	public final static Logger _logger = LoggerFactory.getLogger(Start.class);

}
