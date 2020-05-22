package org.tat.fni.api.common;

import java.lang.reflect.Field;
import java.util.Date;

import org.eclipse.persistence.descriptors.DescriptorEvent;
import org.eclipse.persistence.descriptors.DescriptorEventAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.tat.fni.api.domain.MedicalClaim;

@Component
public class IDInterceptor extends DescriptorEventAdapter {
	private static ICustomIDGenerator customIDGenerator;

	private static IUserProcessService userProcessService;

	private static final String HOSPITALIZED_CLAIM = "org.ace.insurance.medical.claim.HospitalizedClaim";

	private static final String DEATH_CLAIM = "org.ace.insurance.medical.claim.DeathClaim";

	private static final String OPERATION_CLAIM = "org.ace.insurance.medical.claim.OperationClaim";

	private static final String MEDICATION_CLAIM = "org.ace.insurance.medical.claim.MedicationClaim";

	@Autowired(required = true)
	@Qualifier("CustomIDGenerator")
	public void setcustomIDGenerator(ICustomIDGenerator generator) {
		customIDGenerator = generator;
	}

	private String getPrefix(Class cla) {
		return customIDGenerator.getPrefix(cla);
	}

	@Autowired(required = true)
	@Qualifier("UserProcessService")
	public void setUserProcessService(IUserProcessService userProcessService) {
		IDInterceptor.userProcessService = userProcessService;
	}

	@Override
	public void preInsert(DescriptorEvent event) {
		try {
			Object obj = event.getObject();
			Class cla = checkClassType(obj);
			System.out.println(cla.getName());
			Field field = getClassFiled(cla, "id");
			field.setAccessible(true);
			String id = (String) field.get(obj);
			id = FormatID.formatId(id, getPrefix(cla), 10);
			field.set(obj, id);
			Field recorderField = cla.getDeclaredField("recorder");
			recorderField.setAccessible(true);
			UserRecorder recorder = new UserRecorder();
			recorder.setCreatedUserId(userProcessService.getLoginUser().getId());
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
			Class cla = checkClassType(obj);
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

	private Class checkClassType(Object obj) {
		String className = obj.getClass().getName();
		if (className.equals(HOSPITALIZED_CLAIM))
			return MedicalClaim.class;
		if (className.equals(DEATH_CLAIM))
			return MedicalClaim.class;
		if (className.equals(OPERATION_CLAIM))
			return MedicalClaim.class;
		if (className.equals(MEDICATION_CLAIM))
			return MedicalClaim.class;
		return obj.getClass();
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
