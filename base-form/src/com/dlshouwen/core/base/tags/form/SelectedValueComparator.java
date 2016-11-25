package com.dlshouwen.core.base.tags.form;

import java.beans.PropertyEditor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.support.BindStatus;

@SuppressWarnings("rawtypes")
abstract class SelectedValueComparator {

	public static boolean isSelected(BindStatus bindStatus, Object candidateValue) {
		if (bindStatus == null) {
			return (candidateValue == null);
		}

		Object boundValue = bindStatus.getValue();
		if (ObjectUtils.nullSafeEquals(boundValue, candidateValue)) {
			return true;
		}
		Object actualValue = bindStatus.getActualValue();
		if (actualValue != null && actualValue != boundValue &&
				ObjectUtils.nullSafeEquals(actualValue, candidateValue)) {
			return true;
		}
		if (actualValue != null) {
			boundValue = actualValue;
		}
		else if (boundValue == null) {
			return false;
		}

		boolean selected = false;
		if (boundValue.getClass().isArray()) {
			selected = collectionCompare(CollectionUtils.arrayToList(boundValue), candidateValue, bindStatus);
		}
		else if (boundValue instanceof Collection) {
			selected = collectionCompare((Collection) boundValue, candidateValue, bindStatus);
		}
		else if (boundValue instanceof Map) {
			selected = mapCompare((Map) boundValue, candidateValue, bindStatus);
		}
		if (!selected) {
			selected = exhaustiveCompare(boundValue, candidateValue, bindStatus.getEditor(), null);
		}
		return selected;
	}

	private static boolean collectionCompare(Collection boundCollection, Object candidateValue, BindStatus bindStatus) {
		try {
			if (boundCollection.contains(candidateValue)) {
				return true;
			}
		}
		catch (ClassCastException ex) {
		}
		return exhaustiveCollectionCompare(boundCollection, candidateValue, bindStatus);
	}

	private static boolean mapCompare(Map boundMap, Object candidateValue, BindStatus bindStatus) {
		try {
			if (boundMap.containsKey(candidateValue)) {
				return true;
			}
		}
		catch (ClassCastException ex) {
		}
		return exhaustiveCollectionCompare(boundMap.keySet(), candidateValue, bindStatus);
	}

	private static boolean exhaustiveCollectionCompare(
			Collection collection, Object candidateValue, BindStatus bindStatus) {

		Map<PropertyEditor, Object> convertedValueCache = new HashMap<PropertyEditor, Object>(1);
		PropertyEditor editor = null;
		boolean candidateIsString = (candidateValue instanceof String);
		if (!candidateIsString) {
			editor = bindStatus.findEditor(candidateValue.getClass());
		}
		for (Object element : collection) {
			if (editor == null && element != null && candidateIsString) {
				editor = bindStatus.findEditor(element.getClass());
			}
			if (exhaustiveCompare(element, candidateValue, editor, convertedValueCache)) {
				return true;
			}
		}
		return false;
	}

	private static boolean exhaustiveCompare(Object boundValue, Object candidate,
			PropertyEditor editor, Map<PropertyEditor, Object> convertedValueCache) {

		String candidateDisplayString = ValueFormatter.getDisplayString(candidate, editor, false);
		if (boundValue.getClass().isEnum()) {
			Enum boundEnum = (Enum) boundValue;
			String enumCodeAsString = ObjectUtils.getDisplayString(boundEnum.name());
			if (enumCodeAsString.equals(candidateDisplayString)) {
				return true;
			}
			String enumLabelAsString = ObjectUtils.getDisplayString(boundEnum.toString());
			if (enumLabelAsString.equals(candidateDisplayString)) {
				return true;
			}
		}
		else if (ObjectUtils.getDisplayString(boundValue).equals(candidateDisplayString)) {
			return true;
		}
		else if (editor != null && candidate instanceof String) {
			String candidateAsString = (String) candidate;
			Object candidateAsValue;
			if (convertedValueCache != null && convertedValueCache.containsKey(editor)) {
				candidateAsValue = convertedValueCache.get(editor);
			}
			else {
				editor.setAsText(candidateAsString);
				candidateAsValue = editor.getValue();
				if (convertedValueCache != null) {
					convertedValueCache.put(editor, candidateAsValue);
				}
			}
			if (ObjectUtils.nullSafeEquals(boundValue, candidateAsValue)) {
				return true;
			}
		}
		return false;
	}

}
