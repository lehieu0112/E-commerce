<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_manager.jsp"/>

<section class="management_page">
    <form style="width:530px;" action="fileupload3" method="post" enctype="multipart/form-data">
            <input style="width:500px;" type="file" name="file"><br>
            <input type="submit" value="Upload Image" >
        </form> 
    <form  class="edit_product" action="<c:url value="/promotionsmanager"/>" method="post">
        <input type="hidden" name="action" value="addnewpromotion">
        <div id="profile_image" style="width: 400px;height: 200px;"><img src="<c:url value="${pictureLink}"/>"
                                     width="400" height="200"></div>

        <label>Image Link:</label>
        <input type="text" name="pictureLink" value="${pictureLink}" required=""><span class="required">*</span><br>
        <label>Promotion ID : </label>
        <input type="text" name="promotionID" required><span class="required">*</span><br>
        <label style="clear: both;">Promotion Name:</label>
        <input class="editproductInput" type="text" name="promotionName"  required><span class="required">*</span><br>     
        <label style="clear: both;">Edit Descriptions :</label>
        <textarea required class="editproductInput" cols="40" rows="5" name="promotionDescription"></textarea><span class="required">*</span><br>
        <label>Start Date :</label>
        <input type="text" name="startDay"  placeholder="2015-12-31" required pattern="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))"><span class="required">*</span><br>
        <label>End Date :</label>
        <input type="text" name="endDay"  placeholder="2015-12-31" required pattern="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))"><span class="required">*</span><br>
        <label>Discount :</label>
        <input type="text" name="promotionDiscount" required><span class="required">*</span><br>
        
        <input type="submit" value="Add Promotion" id="updatecustomer">
    </form><br>
    <h3>${addmessage}</h3>
</section>

<c:import url="/footer.jsp"/>