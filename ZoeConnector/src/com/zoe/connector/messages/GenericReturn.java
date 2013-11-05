package com.zoe.connector.messages;

/**
 * This class is the model class for generic return type
 * 
 * @author Satish
 * 
 */
public class GenericReturn {

	public int returnValue;

	public int isValid;

	/**
	 * @return the returnValue
	 */
	public int getReturnValue() {
		return returnValue;
	}

	/**
	 * @param returnValue
	 *            the returnValue to set
	 */
	public void setReturnValue(int returnValue) {
		this.returnValue = returnValue;
	}

	/**
	 * @return the isValid
	 */
	public int getIsValid() {
		return isValid;
	}

	/**
	 * @param isValid
	 *            the isValid to set
	 */
	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GenericReturn [returnValue=");
		builder.append(returnValue);
		builder.append(", isValid=");
		builder.append(isValid);
		builder.append("]");
		return builder.toString();
	}

}
