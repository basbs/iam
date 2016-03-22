<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<o:header title="Log In" />
<o:topbar />
<div class="container-fluid main">

	<h1><spring:message code="login.login_with_username_and_password"/></h1>

	<c:if test="${ param.error != null }">
		<div class="alert alert-error"><spring:message code="login.error"/></div>
	</c:if>

  <c:url var="loginUrl" value="/login"/>
	
  <div class="row-fluid">
		<div class="span6 offset1 well">
			<form action="${loginUrl}" method="post">
				<div>
					<div class="input-prepend input-block-level">
						<span class="add-on"><i class="icon-user"></i></span>
						<input type="text" placeholder="Username" autocorrect="off" autocapitalize="off" autocomplete="off" spellcheck="false" value="<c:out value="${ login_hint }" />" id="username" name="username">
					</div>
				</div>
				<div>
					<div class="input-prepend input-block-level">
						<span class="add-on"><i class="icon-lock"></i></span>
						<input type="password" placeholder="Password" autocorrect="off" autocapitalize="off" autocomplete="off" spellcheck="false" id="password" name="password">
					</div>
				</div>
				<div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input type="submit" class="btn" value="Login" name="submit">
				</div>
			</form>
		</div>
	</div>
	
	<div class="row-fluid">
		<div class="span6 offset1 well">
			<form action="openid_connect_login" method="get">
				<div>
					<input type="hidden" name="identifier" value="" />
					<input type="submit" class="btn" value="Login con IDP esterno" name="submit">
				</div>
			</form>
		</div>
	</div>
</div>

<o:footer/>
