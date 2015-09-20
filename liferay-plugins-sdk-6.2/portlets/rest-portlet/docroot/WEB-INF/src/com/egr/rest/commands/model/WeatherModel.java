package com.egr.rest.commands.model;

public class WeatherModel {
	
	protected String _status="yessssssssssssssss";
	protected String _some=" says he's against claims";
		
	//
	// JAVA API
	//
	/**
	 * Overrode in order to ...
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "status='"+_status+"'   some='"+_some+"'";
	}
	//
	// WO API
	//

	//
	// state indicator methods
	//

	//
	// action methods
	//

	//
	// misc.
	//

	//
	// abstract/interface methods
	//

	//
	// accessor methods
	//
	public String getStatus() {
		return _status;
	}
	public void setStatus(String _status) {
		this._status = _status;
	}

	public String getSome() {
		return _some;
	}
	public void setSome(String some) {
		_some = some;
	}
	//
	// inner classes
	//

}
