/**
 *
 * Todo.txt Touch/src/com/todotxt/todotxttouch/task/Task.java
 *
 * Copyright (c) 2009-2011 mathias, Gina Trapani, Tim Barlotta
 *
 * LICENSE:
 *
 * This file is part of Todo.txt Touch, an Android app for managing your todo.txt file (http://todotxt.com).
 *
 * Todo.txt Touch is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any
 * later version.
 *
 * Todo.txt Touch is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with Todo.txt Touch.  If not, see
 * <http://www.gnu.org/licenses/>.
 *
 * @author mathias <mathias[at]x2[dot](none)>
 * @author Gina Trapani <ginatrapani[at]gmail[dot]com>
 * @author Tim Barlotta <tim[at]barlotta[dot]net>
 * @license http://www.gnu.org/licenses/gpl.html
 * @copyright 2009-2011 mathias, Gina Trapani, Tim Barlotta
 */
package com.todotxt.todotxttouch.task;

import com.todotxt.todotxttouch.Util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Task implements Serializable {
	public static final char NO_PRIORITY = '-';
	private static final String COMPLETED = "x ";
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private long id;
	private char priority;
	private boolean deleted = false;
	private boolean completed = false;
	private String text;
	private String prependedDate;
	private List<String> contexts;
	private List<String> projects;

	public Task(long id, String rawText) {
		this.id = id;
		this.init(rawText);
	}

	public void update(String rawText) {
		this.init(rawText);
	}

	private void init(String rawText) {
		TextSplitter splitter = TextSplitter.getInstance();
		TextSplitter.SplitResult splitResult = splitter.split(rawText);
		this.priority = splitResult.priority;
		this.text = splitResult.text;
		this.prependedDate = splitResult.prependedDate;

		this.contexts = ContextParser.getInstance().parse(text);
		this.projects = ProjectParser.getInstance().parse(text);
		this.deleted = Util.isEmpty(text);
		this.completed = text.startsWith(COMPLETED);
	}

	public String getText() {
		return text;
	}

	public long getId() {
		return id;
	}

	public void setPriority(char priority) {
		this.priority = priority;
	}

	public char getPriority() {
		return priority;
	}

	public List<String> getContexts() {
		return contexts;
	}

	public List<String> getProjects() {
		return projects;
	}

	public String getPrependedDate() {
		return prependedDate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void markComplete(Date date) {
		if (!this.completed) {
			this.priority = Task.NO_PRIORITY;
			this.prependedDate = "";
			String formattedDate = new SimpleDateFormat(Task.DATE_FORMAT)
					.format(date);
			this.text = Task.COMPLETED + formattedDate + " " + text;
			this.deleted = false;
			this.completed = true;
		}
	}

	public void markIncomplete() {
		if (this.completed) {
			this.text = this.text.substring(13);
			this.completed = false;
		}
	}

	public String inFileFormat() {
		StringBuilder sb = new StringBuilder();
		if (!this.isCompleted()) {
			if (this.priority >= 'A' && this.priority <= 'Z') {
				sb.append("(");
				sb.append(this.priority);
				sb.append(") ");
			}
			if (!this.prependedDate.equalsIgnoreCase("")) {
				sb.append(this.prependedDate + " ");
			}
		}
		sb.append(this.text);
		return sb.toString();
	}

	public Task copy() {
		return new Task(this.id, this.inFileFormat());
	}

	public void copyInto(Task destination) {
		destination.id = this.id;
		destination.init(this.inFileFormat());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Task task = (Task) o;

		if (id != task.id) {
			return false;
		}
		if (priority != task.priority) {
			return false;
		}
		if (contexts != null ? !contexts.equals(task.contexts)
				: task.contexts != null) {
			return false;
		}
		if (prependedDate != null ? !prependedDate.equals(task.prependedDate)
				: task.prependedDate != null) {
			return false;
		}
		if (projects != null ? !projects.equals(task.projects)
				: task.projects != null) {
			return false;
		}
		if (text != null ? !text.equals(task.text) : task.text != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) priority;
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result
				+ (prependedDate != null ? prependedDate.hashCode() : 0);
		result = 31 * result + (contexts != null ? contexts.hashCode() : 0);
		result = 31 * result + (projects != null ? projects.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[id=").append(id).append("]");
		sb.append("[prio=").append(priority).append("]");
		sb.append("[text=").append(text).append("]");
		sb.append("[deleted=").append(this.isDeleted()).append("]");
		sb.append("[completed=").append(this.isCompleted()).append("]");
		// contexts
		sb.append("[contexts:");
		for (String cxt : contexts) {
			sb.append("[context=").append(cxt).append("]");
		}
		sb.append("]");
		// projects
		sb.append("[projects:");
		for (String prj : projects) {
			sb.append("[project=").append(prj).append("]");
		}
		sb.append("]");
		return sb.toString();
	}

}