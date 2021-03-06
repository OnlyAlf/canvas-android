package com.instructure.canvasapi2.pact.canvas.student

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.model.RequestResponsePact
import com.instructure.canvasapi2.apis.CourseAPI
import com.instructure.canvasapi2.builders.RestBuilder
import com.instructure.canvasapi2.models.Course
import com.instructure.canvasapi2.utils.weave.awaitApi
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking

class GetCourse : CanvasPact() {

    private val expectedCourse = Course(
            id = 1,
            name = "Some Course",
            courseCode = "SC"
    )

    private val authUser = "Student1"

    override fun createPact(builder: PactDslWithProvider): RequestResponsePact {
        val body = PactDslJsonBody()
                .integerType("id", expectedCourse.id)
                .stringType("name", expectedCourse.name)
                .stringType("course_code", expectedCourse.courseCode)
                .asBody()
        return builder
                .given("a student enrolled in a course")
                .uponReceiving("a get request for the course as a student")
                .method("GET")
                .path("/api/v1/courses/1")
                .query("include[]=term&include[]=permissions&include[]=license&include[]=is_public&include[]=needs_grading_count&include[]=course_image")
                .headers(mapOf(Pair("Authorization", "Bearer some_token"), Pair("Auth-user", authUser)))
                .willRespondWith()
                .status(200)
                .body(body)
                .headers(mapOf(Pair("Content-Type", "application/json; charset=utf-8")))
                .toPact()
    }

    override fun runTest(mockServer: MockServer) {
        runBlocking {
            val response = awaitApi<Course> {
                val adapter = RestBuilder(it, authUser)
                CourseAPI.getCourse(1, adapter, it, getParams(mockServer))
            }

            assertEquals(expectedCourse.id, response.id)
            assertEquals(expectedCourse.name, response.name)
            assertEquals(expectedCourse.courseCode, response.courseCode)
        }
    }
}
