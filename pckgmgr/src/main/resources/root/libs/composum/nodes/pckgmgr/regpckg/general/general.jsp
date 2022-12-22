<%@page session="false" pageEncoding="utf-8" %>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.2" %>
<%@taglib prefix="cpn" uri="http://sling.composum.com/cpnl/1.0" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sling:defineObjects/>

<cpn:component id="pckg" type="com.composum.sling.core.pckgmgr.regpckg.view.PackageBean" scope="request">
    <%--@elvariable id="pckg" type="com.composum.sling.core.pckgmgr.regpckg.view.PackageBean"--%>
    <div class="detail-panel package">
        <div class="package-detail">
            <c:forEach items="${pckg.allVersions}" var="version">
                <sling:include replaceSuffix="${version.path}" replaceSelectors="listitem"
                               resourceType="composum/nodes/pckgmgr/version/general"/>
            </c:forEach>
        </div>
    </div>
</cpn:component>
