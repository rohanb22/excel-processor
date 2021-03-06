

public class Info {

	private int recordNumber;
    private String name;
    private String mobile;
    private String phone;
    private String permAddress;
    private String commAddress;
    private String errorDescription;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPermAddress() {
        return permAddress;
    }

    public void setPermAddress(String permAddress) {
        this.permAddress = permAddress;
    }

    public String getCommAddress() {
        return commAddress;
    }

    public void setCommAddress(String commAddress) {
        this.commAddress = commAddress;
    }

    public int getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(int recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	@Override
    public String toString() {
        return "Info [Name=" + name + ", Mobile=" + mobile + ", Phone=" + phone + ", Permanent Address=" + permAddress
                + ", Communication Address=" + commAddress + "]";
    }

}