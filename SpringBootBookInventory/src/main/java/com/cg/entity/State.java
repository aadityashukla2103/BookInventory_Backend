package com.cg.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "state")
public class State {

    @Id
    @Column(name = "StateCode", length = 2)
    private String stateCode;

    @Column(name = "StateName" , length = 50)
    private String stateName;

    @OneToMany(mappedBy = "state")
    private List<Publisher> publishers;

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<Publisher> getPublishers() {
		return publishers;
	}

	public void setPublishers(List<Publisher> publishers) {
		this.publishers = publishers;
	}

    
}