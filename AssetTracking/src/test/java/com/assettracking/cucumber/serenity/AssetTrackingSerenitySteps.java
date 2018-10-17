package com.assettracking.cucumber.serenity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.assettracking.model.AssetTrackingClass;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.Validatable;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Title;

public class AssetTrackingSerenitySteps {
	
	//This is to create a reusable method
	@Step("Creating Data with firstName:{0}, lastname:{1}, email:{2}, programme:{3}, courses:{4}")
	public ValidatableResponse createData (String firstName, String lastName, String email, String programme, 
			List<String> courses){
		
		AssetTrackingClass assettracking = new AssetTrackingClass();
		assettracking.setFirstName(firstName);
		assettracking.setLastName(lastName);
		assettracking.setEmail(email);
		assettracking.setProgramme(programme);
		assettracking.setCourses(courses);
		
		return SerenityRest.rest().given()
		.contentType(ContentType.JSON)
		.when()
		.body(assettracking)
		.post()
		.then();
		
	}
	
	//This is to create a reusable method
	@Step ("Getting the Data Information with firstname: {0}")
	public HashMap<String, Object> getDataInfoByFirstName(String firstName){
		String p1 = "findAll{it.firstName=='";
		String p2 = "'}.get(0)";
		
		return SerenityRest.rest().given()
		.when()
		.get("/list")
		.then()
		.log()
		.all()
		.statusCode(200)
		.extract()
		.path(p1+firstName+p2);
		
	}
	
	@Step("Update information from pulse end-point with studentID: {0}, firstName:{1}, lastName{2}, email{3}, programme{4}, courses{5}")
	public Validatable<ValidatableResponse,Response> updatePulseEndpoint(int studentid, String firstName, String lastName, String email, String programme, 
			List<String> courses){
		
		
		AssetTrackingClass assettracking = new AssetTrackingClass();
		assettracking.setFirstName(firstName);
		assettracking.setLastName(lastName);
		assettracking.setEmail(email);
		assettracking.setProgramme(programme);
		assettracking.setCourses(courses);
		
		return SerenityRest.rest().given().contentType(ContentType.JSON).log().all()
		.when().body(assettracking).put("/"+studentid);
		
	}
	
	@Step("Deleting information from pulse end-point with ID {0}")
	public void DeletingData(int studentId){
		SerenityRest.rest().given().when().delete("/"+studentId);
		
		}
	
	@Step("Getting information from the end-pont with ID: {0}")
	public ValidatableResponse getStudentById(int studentId){
		return
		SerenityRest
		.rest()
		.given()
		.when()
		.get("/" + studentId).then();
	}

}
