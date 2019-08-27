<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<%-- <dl class="vevent">
	<div style1="min-width:500px;margin-top:15px;float: left;-webkit-border-radius: 20px;-moz-border-radius: 20px;border-radius: 20px;border:3px solid #96C0FF;background:rgba(255,255,255,0.5);-webkit-box-shadow: #B3B3B3 4px 4px 4px;-moz-box-shadow: #B3B3B3 4px 4px 4px; box-shadow: #B3B3B3 4px 4px 4px;">
		<div style="float: left; margin-top: 20px; margin-left: 100px;" id="jobCategoryLeft">
			<div style="width:160px;padding: 10px; border-radius: 4px; -moz-border-radius: 4px; -webkit-border-radius: 14px; border: 1px solid #CCC;">
				<p>
					<a href="<c:url value="/candidate/candidate-profile"/>">
						<strong>JOB SEEKER <br/> Looking for a Job?</strong>
					</a>
				</p>
			</div>
		</div>
		<div style="float: left;padding-left:10px; margin: 20px 0px 0px 15px; ">
			<div style="padding: 10px; border-radius: 4px; -moz-border-radius: 4px; -webkit-border-radius: 14px; border: 1px solid #CCC;">
				<p>
					<a href="<c:url value="/employer/company-profile"/>">
						<strong>EMPLOYER <br/> Looking for HUBZone?</strong>
					</a>
				</p>
			</div>
		</div>
	</div>
</dl>
<div style="float: left; clear: both"></div> --%>

		<!-- <div class="pagewrapper"> -->
			<!-- <div class="header">
			</div>
			<div class="top-nav" id="menu">
			<li class="current_page_item"><a href="#">Home</a></li>
			<li><a href="#">Candidate Registration</a></li>
			<li><a href="#">Employer Registration</a></li>
			</div> -->
			<div class="mainbody">
                <div class="section1">
                    <!-- <h3><b>Matching HUBZone Job Seekers with HUBZone Companies</b></h3> -->
                    <p>The Federal Government is looking to bring jobs to
                        HUBZone (Historically Underutilized Business</p>
                    <p>Zone) areas.We are here to help bring job
                    seekers and HUBZone employers together.</p>
                    <h2 class="learnMoreLink" style="">
                        <a href="http://sba.gov/hubzone/" target="_blank">Learn More</a>
                    </h2>
                </div>
				<div class="section2">
					<div class="job-seekers">
					    <img src="/hubzone/resources/images/jobSeeker.jpg" />
						<figure>
                            <figcaption>Job-Seekers looking</figcaption>
                            <figcaption>for a Job</figcaption>
                                <a href="<c:url value="/candidatelogin"/>">
                                <%-- <security:authorize access="hasAnyRole('ROLE_EMP')">
                                <a href="<c:url value="/loginfailed"/>"></a>
                                </security:authorize> --%>
                                    <!-- <img src="/resources/images/jobseekers.png" alt="Job Sekeers"></img> -->
                                    <img src="<c:url value="/resources/images/clickHereBtn.png"/>"/>
                                </a>

                        </figure>
					</div>
					<div class="employers">
                        <img src="/hubzone/resources/images/employers.png" />
						<figure>
                            <figcaption>Employers looking for HUBZone Job-Seekers</figcaption>
                                <a href="<c:url value="/employerlogin"/>">
                                <%-- <a href="<c:url value="/employer/employer-temp-page"/>"> --%>
                                <%-- <security:authorize access="hasAnyRole('ROLE_CAN')">
                                <a href="<c:url value="/loginfailed"/>"></a>
                                </security:authorize> --%>
                                    <!-- <img src="/resources/images/employer.png"></img> -->
                                    <img src="<c:url value="/resources/images/clickHereBtn.png"/>"/>
                                </a>
						</figure>
					</div>
				</div>
			</div>
		<!-- </div> -->
	

