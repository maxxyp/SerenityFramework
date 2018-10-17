package com.assettracking.junit.assettrackingInfo;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.assettracking.cucumber.serenity.AssetTrackingSerenitySteps;
import com.assettracking.model.AssetTrackingClass;
import com.assettracking.testbase.TestBase;
import com.assettracking.utils.TestUtils;

import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssetTrackingCRUDTest extends TestBase{
	
	static String firstName = "SHARON"+TestUtils.getRandomValue();
	static String lastName = "CHARLOTTE"+TestUtils.getRandomValue();
	static String programme = "ComputerScience";
	static String email = TestUtils.getRandomValue()+"xyz@gmail.com";
	static int studentId;
	
	@Steps
	AssetTrackingSerenitySteps steps;
	
	@Title("This test will create a Data to Asset Tracking")
	@Test
	public void test001(){
		
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("C++");
		
		steps.createData(firstName, lastName, email, programme, courses);
	
	}
	
	@Title("Verify if Data was added to the application")
	@Test
	public void test002(){

		
		HashMap<String, Object> value = steps.getDataInfoByFirstName(firstName);
		 assertThat(value,hasValue(firstName));
		 studentId = (int) value.get("id");
		
	}
	
	@Title("Update information from pulse end-point")
	@Test
	public void test003(){

		ArrayList<String> courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("C++");
		
		firstName = firstName+"_Updated";
		
		steps.updatePulseEndpoint(studentId, firstName, lastName, email, programme, courses);
		
		HashMap<String, Object> value = steps.getDataInfoByFirstName(firstName);
		assertThat(value, hasValue(firstName));

	}
	
	@Title("Delete information from pulse end-point and verify its Deleted!")
	@Test
	public void test004(){
		steps.DeletingData(studentId);
		steps.getStudentById(studentId).statusCode(404);
	}

}
