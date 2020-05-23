package org.tat.fni.api.common;

import java.lang.reflect.Field;
import java.util.Date;

import org.eclipse.persistence.descriptors.DescriptorEvent;
import org.eclipse.persistence.descriptors.DescriptorEventAdapter;
import org.springframework.stereotype.Component;

@Component
public class IDInterceptor extends DescriptorEventAdapter {
//	private static ICustomIDGenerator customIDGenerator;



//	@Autowired(required = true)
//	@Qualifier("CustomIDGenerator")
//	public void setcustomIDGenerator(ICustomIDGenerator generator) {
//		customIDGenerator = generator;
//	}
//
//	private String getPrefix(Class cla) {
//		return customIDGenerator.getPrefix(cla);
//	}


	@Override
	public void preInsert(DescriptorEvent event) {
		try {
			Object obj = event.getObject();
			Class cla = obj.getClass();
			Field field = getClassFiled(cla, "id");
			field.setAccessible(true);
			String id = (String) field.get(obj);
//			id = FormatID.formatId(id, getPrefix(cla), 10);
			field.set(obj, id);
			Field recorderField = cla.getDeclaredField("recorder");
			recorderField.setAccessible(true);
			UserRecorder recorder = new UserRecorder();
//			recorder.setCreatedUserId(userProcessService.getLoginUser().getId());
			recorder.setCreatedDate(new Date());
			recorderField.set(obj, recorder);

		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void preUpdateWithChanges(DescriptorEvent event) {
		try {
			Object obj = event.getObject();
			Class cla = obj.getClass();
			Field recorderField = cla.getDeclaredField("recorder");
			recorderField.setAccessible(true);
			UserRecorder recorder = (UserRecorder) recorderField.get(obj);
			recorder = (recorder == null) ? new UserRecorder() : recorder;
//			recorder.setUpdatedUserId(userProcessService.getLoginUser().getId());
			recorder.setUpdatedDate(new Date());
			recorderField.set(obj, recorder);
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public Field getClassFiled(Class<?> c, String fieldName) {
		Field field = null;
		try {
			field = c.getDeclaredField(fieldName);

		}
		catch (NoSuchFieldException e) {
			Class<?> sc = c.getSuperclass();
			field = getClassFiled(sc, fieldName);
		}
		return field;
	}

}
