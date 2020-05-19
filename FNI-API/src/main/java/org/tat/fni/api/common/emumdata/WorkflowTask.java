package org.tat.fni.api.common.emumdata;

public enum WorkflowTask {
	PROPOSAL("Proposal"), 
	SURVEY("Survey"), 
	APPROVAL("Approval"), 
	AUTHORISE("Authorised"), 
	INFORM("Inform"), 
	CONFIRMATION("Confirmation"), 
	PAYMENT("Payment"), 
	ISSUING("Issue"), 
	REQUEST("Request"), 
	REJECT("Reject"),
	FINISHED("Finished");

	private String label;

	private WorkflowTask(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
