<%@ page language="java" pageEncoding="UTF-8"%>

<table id="advanceSearchTable" class="editTable">
	<tr>
		<td style="width:30px;"></td>
		<td>
			<input type="text" class="field" name="field">
			<input type="text" class="op" name="op">
			<input type="text" class="value" name="value">
			<input type="text" class="join" name="join">
			<a id="addCondition" href="javascript:void(0)"></a>
		</td>
	</tr>
</table>

<script>
	var html = '<tr><td style="width:30px;"></td><td><input type="text" class="field" name="field"> <input type="text" class="op" name="op"> <input type="text" class="value" name="value"> <input type="text" class="join" name="join"> <a class="deleteCondition" href="javascript:void(0)"></a></td></tr>';
	$("#addCondition").on('click', function() {
		$("#advanceSearchTable").append(html);
		$(this).trigger(topJUI.eventType.initUI.advanceSearchForm);
	});
</script>