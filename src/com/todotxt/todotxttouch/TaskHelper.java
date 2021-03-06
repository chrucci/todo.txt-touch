/**
 *
 * Todo.txt Touch/src/com/todotxt/todotxttouch/TaskHelper.java
 *
 * Copyright (c) 2009-2011 mathias, Gina Trapani
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
 * @author mathias <mathias[at]ws7862[dot](none)>
 * @author Gina Trapani <ginatrapani[at]gmail[dot]com>
 * @author mathias <mathias[at]x2[dot](none)>
 * @license http://www.gnu.org/licenses/gpl.html
 * @copyright 2009-2011 mathias, Gina Trapani
 */
package com.todotxt.todotxttouch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.todotxt.todotxttouch.task.Priority;
import com.todotxt.todotxttouch.task.Task;

public class TaskHelper {

	public static String toString(char prio) {
		return prio >= 'A' && prio <= 'Z' ? "" + prio : "";
	}

	public static List<Task> getByPrio(List<Task> items, Priority priority) {
		List<Task> res = new ArrayList<Task>();
		for (Task item : items) {
			if (item.getPriority() == priority) {
				res.add(item);
			}
		}
		return res;
	}

	public static List<Task> getByPrio(List<Task> items, List<String> prios) {
		List<Task> res = new ArrayList<Task>();
		for (Task item : items) {
			if (prios.contains("" + item.getPriority())) {
				res.add(item);
			}
		}
		return res;
	}

	public static List<Task> getByContext(List<Task> items, String context) {
		List<Task> res = new ArrayList<Task>();
		for (Task item : items) {
			if (item.getContexts().contains(context)) {
				res.add(item);
			}
		}
		return res;
	}

	public static List<Task> getByContext(List<Task> items,
			List<String> contexts) {
		List<Task> res = new ArrayList<Task>();
		for (Task item : items) {
			for (String cxt : item.getContexts()) {
				if (contexts.contains(cxt)) {
					res.add(item);
					break;
				}
			}
		}
		return res;
	}

	public static List<Task> getByProject(List<Task> items,
			List<String> projects) {
		List<Task> res = new ArrayList<Task>();
		for (Task item : items) {
			for (String cxt : item.getProjects()) {
				if (projects.contains(cxt)) {
					res.add(item);
					break;
				}
			}
		}
		return res;
	}

	public static List<Task> getByText(List<Task> items, String text) {
		List<Task> res = new ArrayList<Task>();
		for (Task item : items) {
			if (item.getText().contains(text)) {
				res.add(item);
			}
		}
		return res;
	}

	public static List<Task> getByTextIgnoreCase(List<Task> items, String text) {
		text = text.toUpperCase();
		List<Task> res = new ArrayList<Task>();
		for (Task item : items) {
			if (item.getText().toUpperCase().contains(text)) {
				res.add(item);
			}
		}
		return res;
	}

	public static ArrayList<String> getPrios(List<Task> items) {
		Set<String> res = new HashSet<String>();
		for (Task item : items) {
			res.add(item.getPriority().toString());
		}
		ArrayList<String> ret = new ArrayList<String>(res);
		Collections.sort(ret);
		return ret;
	}

	public static ArrayList<String> getContexts(List<Task> items) {
		Set<String> res = new HashSet<String>();
		for (Task item : items) {
			res.addAll(item.getContexts());
		}
		ArrayList<String> ret = new ArrayList<String>(res);
		Collections.sort(ret);
		return ret;
	}

	public static ArrayList<String> getProjects(List<Task> items) {
		Set<String> res = new HashSet<String>();
		for (Task item : items) {
			res.addAll(item.getProjects());
		}
		ArrayList<String> ret = new ArrayList<String>(res);
		Collections.sort(ret);
		return ret;
	}

	public static Task find(List<Task> tasks, Task task) {
		for (Task task2 : tasks) {
			if (task2.getText().equals(task.getOriginalText())
					&& task2.getPriority() == task.getOriginalPriority()) {
				return task2;
			}
		}
		return null;
	}
}
