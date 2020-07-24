package com.server.master.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Keystrokedata {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Integer userId;
	private float H_period_1;
	private float H_period_2;
	private float H_period_3;
	private float H_period_4;
	private float H_period_5;
	private float H_period_6;
	private float H_period_7;
	private float H_period_8;
	private float H_period_9;
	private float H_period_10;
	private float DD_period_1;
	private float DD_period_2;
	private float DD_period_3;
	private float DD_period_4;
	private float DD_period_5;
	private float DD_period_6;
	private float DD_period_7;
	private float DD_period_8;
	private float DD_period_9;
	private float DD_period_10;
	private float UD_period_1;
	private float UD_period_2;
	private float UD_period_3;
	private float UD_period_4;
	private float UD_period_5;
	private float UD_period_6;
	private float UD_period_7;
	private float UD_period_8;
	private float UD_period_9;
	private float UD_period_10;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public float getH_period_1() {
		return H_period_1;
	}
	public void setH_period_1(float h_period_1) {
		H_period_1 = h_period_1;
	}
	public float getH_period_2() {
		return H_period_2;
	}
	public void setH_period_2(float h_period_2) {
		H_period_2 = h_period_2;
	}
	public float getH_period_3() {
		return H_period_3;
	}
	public void setH_period_3(float h_period_3) {
		H_period_3 = h_period_3;
	}
	public float getH_period_4() {
		return H_period_4;
	}
	public void setH_period_4(float h_period_4) {
		H_period_4 = h_period_4;
	}
	public float getH_period_5() {
		return H_period_5;
	}
	public void setH_period_5(float h_period_5) {
		H_period_5 = h_period_5;
	}
	public float getH_period_6() {
		return H_period_6;
	}
	public void setH_period_6(float h_period_6) {
		H_period_6 = h_period_6;
	}
	public float getH_period_7() {
		return H_period_7;
	}
	public void setH_period_7(float h_period_7) {
		H_period_7 = h_period_7;
	}
	public float getH_period_8() {
		return H_period_8;
	}
	public void setH_period_8(float h_period_8) {
		H_period_8 = h_period_8;
	}
	public float getH_period_9() {
		return H_period_9;
	}
	public void setH_period_9(float h_period_9) {
		H_period_9 = h_period_9;
	}
	public float getH_period_10() {
		return H_period_10;
	}
	public void setH_period_10(float h_period_10) {
		H_period_10 = h_period_10;
	}
	public float getDD_period_1() {
		return DD_period_1;
	}
	public void setDD_period_1(float dD_period_1) {
		DD_period_1 = dD_period_1;
	}
	public float getDD_period_2() {
		return DD_period_2;
	}
	public void setDD_period_2(float dD_period_2) {
		DD_period_2 = dD_period_2;
	}
	public float getDD_period_3() {
		return DD_period_3;
	}
	public void setDD_period_3(float dD_period_3) {
		DD_period_3 = dD_period_3;
	}
	public float getDD_period_4() {
		return DD_period_4;
	}
	public void setDD_period_4(float dD_period_4) {
		DD_period_4 = dD_period_4;
	}
	public float getDD_period_5() {
		return DD_period_5;
	}
	public void setDD_period_5(float dD_period_5) {
		DD_period_5 = dD_period_5;
	}
	public float getDD_period_6() {
		return DD_period_6;
	}
	public void setDD_period_6(float dD_period_6) {
		DD_period_6 = dD_period_6;
	}
	public float getDD_period_7() {
		return DD_period_7;
	}
	public void setDD_period_7(float dD_period_7) {
		DD_period_7 = dD_period_7;
	}
	public float getDD_period_8() {
		return DD_period_8;
	}
	public void setDD_period_8(float dD_period_8) {
		DD_period_8 = dD_period_8;
	}
	public float getDD_period_9() {
		return DD_period_9;
	}
	public void setDD_period_9(float dD_period_9) {
		DD_period_9 = dD_period_9;
	}
	public float getDD_period_10() {
		return DD_period_10;
	}
	public void setDD_period_10(float dD_period_10) {
		DD_period_10 = dD_period_10;
	}
	public float getUD_period_1() {
		return UD_period_1;
	}
	public void setUD_period_1(float uD_period_1) {
		UD_period_1 = uD_period_1;
	}
	public float getUD_period_2() {
		return UD_period_2;
	}
	public void setUD_period_2(float uD_period_2) {
		UD_period_2 = uD_period_2;
	}
	public float getUD_period_3() {
		return UD_period_3;
	}
	public void setUD_period_3(float uD_period_3) {
		UD_period_3 = uD_period_3;
	}
	public float getUD_period_4() {
		return UD_period_4;
	}
	public void setUD_period_4(float uD_period_4) {
		UD_period_4 = uD_period_4;
	}
	public float getUD_period_5() {
		return UD_period_5;
	}
	public void setUD_period_5(float uD_period_5) {
		UD_period_5 = uD_period_5;
	}
	public float getUD_period_6() {
		return UD_period_6;
	}
	public void setUD_period_6(float uD_period_6) {
		UD_period_6 = uD_period_6;
	}
	public float getUD_period_7() {
		return UD_period_7;
	}
	public void setUD_period_7(float uD_period_7) {
		UD_period_7 = uD_period_7;
	}
	public float getUD_period_8() {
		return UD_period_8;
	}
	public void setUD_period_8(float uD_period_8) {
		UD_period_8 = uD_period_8;
	}
	public float getUD_period_9() {
		return UD_period_9;
	}
	public void setUD_period_9(float uD_period_9) {
		UD_period_9 = uD_period_9;
	}
	public float getUD_period_10() {
		return UD_period_10;
	}
	public void setUD_period_10(float uD_period_10) {
		UD_period_10 = uD_period_10;
	}
	
	
	
}
