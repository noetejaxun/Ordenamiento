public class Course implements Comparable<Course> {

    private String _code;
    private String _name;
    private String _section;

    public Course(String code, String name, String section) {
        this._code = code;
        this._name = name;
        this._section = section;
    }

    public void setCode(String code) {
        this._code = code;
    }
    public void setName(String name) {
        this._name = name;
    }
    public void setSection(String section) {
        this._section = section;
    }


    public String getCode() {
        return this._code;
    }
    public String getName() {
        return this._name;
    }
    public String getSection() {
        return this._section;
    }

    @Override
	public int compareTo(Course course) {
		String code = String.format("%010d", Integer.parseInt(course.getCode()));
        String name = course.getName();
        String section = course.getSection();
        String value = code;

		if (App.orderBy == "Code") {
            value = code + name + section;
			return _code.compareTo( value );
		} else if (App.orderBy == "Name") {
            value = name + section;
			return _name.compareTo( value );
		}
		
		return _code.compareTo( value );
	}

    @Override
	public String toString() {
		// return comma delimitation string
        int iCode = Integer.parseInt(this._code);
        String sCode = String.valueOf(iCode);

		return sCode + "," + this._name + "," + this._section;
	}
}