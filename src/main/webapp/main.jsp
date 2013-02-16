<form name="save_passenger_single" id="confirmPassenger" method="post" action="/otsweb/order/confirmPassengerAction.do?method=confirmPassengerInfoSingle"><div><input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="3ff92388c5a8f03f487f68f7040d2ace"></div>
			




<script>
//刷新验证码
function refreshImg(){
	$("#img_rrand_code").attr("src","/otsweb/passCodeAction.do?rand=randp"+'&'+Math.random());
}
//车次信息-确认提交订单时用
var train_date_str_ = "2013-02-19";
var station_train_code_ = "K21";
var from_station_name_ = "石家庄";
var to_station_name_ = "武昌";
var to_station_code_ = "WCN";
var start_time_str_ = "12:50";
var arrive_time_str_ = "23:55";
var lishi_ = "11:05";
var cardflag = false;//是否使用护照
</script>
<script>
$().ready(function() {
	search_class_gray();

	//清空检索文本框的默认值
	$("#passenger_filter_input").focus(function(){
		var search = $("#passenger_filter_input").val();
		if (search == '中文或拼音首字母') {
			search_class_plain();
			$("#passenger_filter_input").val("");
		}
	});

	// 显示检索文本框的默认值
	$("#passenger_filter_input").blur(function(){
		var search = $("#passenger_filter_input").val();
		if (search == '') {
			search_class_gray();
			$("#passenger_filter_input").val("中文或拼音首字母");
		}
	});
});

//文本框样式：正常样式
function search_class_plain() {
	$("#passenger_filter_input").removeClass("input_20txt_gray");
	$("#passenger_filter_input").addClass("input_20txt");
}

// 文本框样式：灰色字体
function search_class_gray() {
	$("#passenger_filter_input").removeClass("input_20txt");
	$("#passenger_filter_input").addClass("input_20txt_gray");
}
</script>
<input type="hidden" name="leftTicketStr" id="left_ticket" value="1012403026403510000410124000363022300002">
	
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="qr_box">
	<colgroup style="width: 20%">
	</colgroup>
	<colgroup style="width: 20%">
	</colgroup>
	<colgroup style="width: 20%">
	</colgroup>
	<colgroup style="width: 20%">
	</colgroup>
	<colgroup style="width: 20%">
	</colgroup>
	<tbody><tr style="background-color: #F3F8FC">
		<td class="bluetext">2013年02月19日</td>
		<td class="bluetext">K21次</td>
		<td class="bluetext">石家庄（12:50 开 ）
		</td>
		<td class="bluetext">武昌（23:55到 ）
		</td>

		<td class="bluetext">历时（11:05）</td>
	</tr>
	
		
		
			<tr>
		
		<td>硬卧(223.00元)2张票</td>
	
		
		
		<td>硬座(124.00元)有票</td>
	
		
		
		<td>软卧(351.00元)4张票</td>
	
		
		
		<td>无座(124.00元)有票</td>
	
	</tr>
	<tr>
		<td colspan="5" class="redtext">以上余票信息随时发生变化，仅作参考</td>
	</tr>
</tbody></table>


	<div class="qr_title">
		我的常用联系人-<span><a href="/otsweb/passengerAction.do?method=initUsualPassenger12306">增加或修改常用联系人</a></span><span style="margin-left: 2%;">快速搜索&nbsp;</span><input name="textfield" type="text" id="passenger_filter_input" value="中文或拼音首字母" class="input_20txt_gray">
	</div>
	<table id="passenger_single_tb_id" width="100%" border="0" cellspacing="1" cellpadding="0" class="qr_box">
		<colgroup style="width: 11%">
		</colgroup>
		<colgroup style="width: 11%">
		</colgroup>
		<colgroup style="width: 11%">
		</colgroup>
		<colgroup style="width: 11%">
		</colgroup>
		<colgroup style="width: 11%">
		</colgroup>
		<colgroup style="width: 11%">
		</colgroup>
		<colgroup style="width: 11%">
		</colgroup>
		<colgroup style="width: 11%">
		</colgroup>

		<tbody><tr>
			<td colspan="8" width="100%"><div id="showPassengerFilter" style="width: 100%;"><div style="float:left;width:110px;" id="cxjavac1110101198701015779"><span><input class="_checkbox_class" type="checkbox" name="checkbox0" id="cxjavac1110101198701015779" value="0"></span><span style="">cxjavac</span></div><div style="float:left;width:110px;" id="testa1110101197001016119"><span><input class="_checkbox_class" type="checkbox" name="checkbox1" id="testa1110101197001016119" value="1"></span><span style="">testa</span></div><div style="float:left;width:110px;" id="testb1110101197001014818"><span><input class="_checkbox_class" type="checkbox" name="checkbox2" id="testb1110101197001014818" value="2"></span><span style="">testb</span></div><div style="float:left;width:110px;" id="testc1110101197001019037"><span><input class="_checkbox_class" type="checkbox" name="checkbox3" id="testc1110101197001019037" value="3"></span><span style="">testc</span></div><div style="float:left;width:110px;" id="testd1110101197001012935"><span><input class="_checkbox_class" type="checkbox" name="checkbox4" id="testd1110101197001012935" value="4"></span><span style="">testd</span></div><div style="float:left;width:110px;" id="teste1110101197001011756"><span><input class="_checkbox_class" type="checkbox" name="checkbox5" id="teste1110101197001011756" value="5"></span><span style="">teste</span></div><div style="float:left;width:110px;" id="胡丹1420581198706080621"><span><input class="_checkbox_class" type="checkbox" name="checkbox6" id="胡丹1420581198706080621" value="6"></span><span style="">胡丹</span></div><div style="float:left;width:110px;" id="陈鑫1510521198703040013"><span><input class="_checkbox_class" type="checkbox" name="checkbox7" id="陈鑫1510521198703040013" value="7"></span><span style="">陈鑫</span></div><div style="clear: both;"></div></div></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		
	</tbody></table>



<div style="width: auto;">
	<div id="error_info"></div>
</div>
<div class="qr_title">
	乘车人信息&nbsp;<span class="base_txtdiv" style="color: #0055AA;" onmouseover="onStopHover_name_cardtype(this)" onmouseout="onStopOut()">(填写说明)</span>
</div>

<input type="hidden" name="orderRequest.train_date" value="2013-02-19" id="start_date">
<input type="hidden" name="orderRequest.train_no" value="2400000K2100" id="train_no">
<input type="hidden" name="orderRequest.station_train_code" value="K21" id="station_train_code">
<input type="hidden" name="orderRequest.from_station_telecode" value="SJP" id="from_station_telecode">
<input type="hidden" name="orderRequest.to_station_telecode" value="WCN" id="to_station_telecode">
<input type="hidden" name="orderRequest.seat_type_code" value="" id="seat_type_code">

<input type="hidden" name="orderRequest.ticket_type_order_num" value="" id="ticket_type_order_num">
<input type="hidden" name="orderRequest.bed_level_order_num" value="000000000000000000000000000000" id="bed_level_order_num">

<input type="hidden" name="orderRequest.start_time" value="12:50" id="orderRequest_start_time">

<input type="hidden" name="orderRequest.end_time" value="23:55" id="orderRequest_end_time">
<input type="hidden" name="orderRequest.from_station_name" value="石家庄" id="orderRequest_from_station_name">
<input type="hidden" name="orderRequest.to_station_name" value="武昌" id="orderRequest_to_station_name">
<input type="hidden" name="orderRequest.cancel_flag" value="1" id="cancel_flag">
<input type="hidden" name="orderRequest.id_mode" value="Y" id="orderRequest_id_mode">
<input type="hidden" value="" id="_type">
<input type="hidden" value="2013-02-19" id="_train_date_str">
<input type="hidden" value="K21" id="_station_train_code">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_qr">
	<tbody><tr>
		<th scope="col">&nbsp;</th>
		<th scope="col">&nbsp;</th>
		<th scope="col">席别</th>
		<th scope="col">票种</th>
		<th scope="col">姓名</th>
		<th scope="col">证件类型</th>
		<th scope="col">证件号码</th>
		<th scope="col">手机号</th>
		<th scope="col"><input type="checkbox" id="checkbox_select_all">
			全部</th>
	</tr>

		<tr class="passenger_class" id="passenger_1" style="">
			<td class="s" style="width: 4%">
			<input type="hidden" name="passengerTickets" id="passenger_1_p"> <input type="hidden" name="oldPassengers" id="passenger_1_old">  
					<a href="#" id="passenger_1_a" class="s">删除</a>
				</td>
			<td style="width: 6%"><div id="passenger_1_index">第1位</div></td>
			<td><select name="passenger_1_seat" id="passenger_1_seat" style="width:74px"><option value="1">硬座</option><option value="3" selected="">硬卧</option><option value="4">软卧</option></select> 
				<select id="passenger_1_seat_detail" name="passenger_1_seat_detail" style=""><option value="0">随机</option><option value="2">上铺</option><option value="2">中铺</option><option value="1">下铺</option></select></td>
			<td><select name="passenger_1_ticket" id="passenger_1_ticket"><option value="1" selected="">成人票</option><option value="2">儿童票</option><option value="3">学生票</option><option value="4">残军票</option></select></td>
			<td><input name="passenger_1_name" type="text" id="passenger_1_name" size="12" maxlength="20" class="input_20txt" value=""></td>
			<td><select name="passenger_1_cardtype" id="passenger_1_cardtype">
					
						<option value="1">二代身份证</option>
					
						<option value="2">一代身份证</option>
					
						<option value="C">港澳通行证</option>
					
						<option value="G">台湾通行证</option>
					
						<option value="B">护照</option>
					
			</select></td>
			<td><input name="passenger_1_cardno" type="text" id="passenger_1_cardno" size="20" maxlength="35" style="text-transform: uppercase;" class="input_20txt" value=""></td>
			<td><input name="passenger_1_mobileno" type="text" id="passenger_1_mobileno" size="11" maxlength="20" class="input_20txt" value=""></td>
			<td style="width: 16%"><label> 
						<input type="checkbox" name="checkbox9" id="passenger_1_isSave" value="Y" checked="">    保存到常用联系人
									      
			</label></td>
		</tr>
	
 
		
			<tr style="" class="passenger_class" id="passenger_2">
				<td style="width: 4%"><input type="hidden" name="passengerTickets" id="passenger_2_p"> <input type="hidden" name="oldPassengers" id="passenger_2_old">  
						<a href="#" id="passenger_2_a" class="s">删除</a>
					</td>
				<td style="width: 6%"><div id="passenger_2_index">第2位</div></td>
				<td>
				<select name="passenger_2_seat" id="passenger_2_seat" style="width:74px"><option value="1">硬座</option><option value="3" selected="">硬卧</option><option value="4">软卧</option></select> 
				<select id="passenger_2_seat_detail" name="passenger_2_seat_detail" style=""><option value="0">随机</option><option value="2">上铺</option><option value="2">中铺</option><option value="1">下铺</option></select></td><td><select name="passenger_2_ticket" id="passenger_2_ticket"><option value="1" selected="">成人票</option><option value="2">儿童票</option><option value="3">学生票</option><option value="4">残军票</option></select></td>
				<td><input name="passenger_2_name" type="text" id="passenger_2_name" size="12" maxlength="30" class="input_20txt" value=""></td>
				<td><select name="passenger_2_cardtype" id="passenger_2_cardtype">
						
							<option value="1">二代身份证</option>
						
							<option value="2">一代身份证</option>
						
							<option value="C">港澳通行证</option>
						
							<option value="G">台湾通行证</option>
						
							<option value="B">护照</option>
						
				</select></td>
				<td><input type="text" id="passenger_2_cardno" name="passenger_2_cardno" size="20" maxlength="20" style="text-transform: uppercase;" class="input_20txt"></td>
				<td><input type="text" id="passenger_2_mobileno" name="passenger_2_mobileno" size="11" maxlength="20" class="input_20txt"></td>
				<td style="width: 16%"><label> 
							<input type="checkbox" name="checkbox9" id="passenger_2_isSave" value="Y" checked="">    保存到常用联系人
									      
				</label></td>
				
			</tr>
		
			<tr style="" class="passenger_class" id="passenger_3">
				<td style="width: 4%"><input type="hidden" name="passengerTickets" id="passenger_3_p"> <input type="hidden" name="oldPassengers" id="passenger_3_old">  
						<a href="#" id="passenger_3_a" class="s">删除</a>
					</td>
				<td style="width: 6%"><div id="passenger_3_index">第3位</div></td>
				<td>
				<select name="passenger_3_seat" id="passenger_3_seat" style="width:74px"><option value="1">硬座</option><option value="3" selected="">硬卧</option><option value="4">软卧</option></select> 
				<select id="passenger_3_seat_detail" name="passenger_3_seat_detail" style=""><option value="0">随机</option><option value="2">上铺</option><option value="2">中铺</option><option value="1">下铺</option></select></td><td><select name="passenger_3_ticket" id="passenger_3_ticket"><option value="1" selected="">成人票</option><option value="2">儿童票</option><option value="3">学生票</option><option value="4">残军票</option></select></td>
				<td><input name="passenger_3_name" type="text" id="passenger_3_name" size="12" maxlength="30" class="input_20txt" value=""></td>
				<td><select name="passenger_3_cardtype" id="passenger_3_cardtype">
						
							<option value="1">二代身份证</option>
						
							<option value="2">一代身份证</option>
						
							<option value="C">港澳通行证</option>
						
							<option value="G">台湾通行证</option>
						
							<option value="B">护照</option>
						
				</select></td>
				<td><input type="text" id="passenger_3_cardno" name="passenger_3_cardno" size="20" maxlength="20" style="text-transform: uppercase;" class="input_20txt"></td>
				<td><input type="text" id="passenger_3_mobileno" name="passenger_3_mobileno" size="11" maxlength="20" class="input_20txt"></td>
				<td style="width: 16%"><label> 
							<input type="checkbox" name="checkbox9" id="passenger_3_isSave" value="Y" checked="">    保存到常用联系人
									      
				</label></td>
				
			</tr>
		
			<tr style="" class="passenger_class" id="passenger_4">
				<td style="width: 4%"><input type="hidden" name="passengerTickets" id="passenger_4_p"> <input type="hidden" name="oldPassengers" id="passenger_4_old">  
						<a href="#" id="passenger_4_a" class="s">删除</a>
					</td>
				<td style="width: 6%"><div id="passenger_4_index">第4位</div></td>
				<td>
				<select name="passenger_4_seat" id="passenger_4_seat" style="width:74px"><option value="1">硬座</option><option value="3" selected="">硬卧</option><option value="4">软卧</option></select> 
				<select id="passenger_4_seat_detail" name="passenger_4_seat_detail" style=""><option value="0">随机</option><option value="2">上铺</option><option value="2">中铺</option><option value="1">下铺</option></select></td><td><select name="passenger_4_ticket" id="passenger_4_ticket"><option value="1" selected="">成人票</option><option value="2">儿童票</option><option value="3">学生票</option><option value="4">残军票</option></select></td>
				<td><input name="passenger_4_name" type="text" id="passenger_4_name" size="12" maxlength="30" class="input_20txt" value=""></td>
				<td><select name="passenger_4_cardtype" id="passenger_4_cardtype">
						
							<option value="1">二代身份证</option>
						
							<option value="2">一代身份证</option>
						
							<option value="C">港澳通行证</option>
						
							<option value="G">台湾通行证</option>
						
							<option value="B">护照</option>
						
				</select></td>
				<td><input type="text" id="passenger_4_cardno" name="passenger_4_cardno" size="20" maxlength="20" style="text-transform: uppercase;" class="input_20txt"></td>
				<td><input type="text" id="passenger_4_mobileno" name="passenger_4_mobileno" size="11" maxlength="20" class="input_20txt"></td>
				<td style="width: 16%"><label> 
							<input type="checkbox" name="checkbox9" id="passenger_4_isSave" value="Y" checked="">    保存到常用联系人
									      
				</label></td>
				
			</tr>
		
			<tr style="" class="passenger_class" id="passenger_5">
				<td style="width: 4%"><input type="hidden" name="passengerTickets" id="passenger_5_p"> <input type="hidden" name="oldPassengers" id="passenger_5_old">  
						<a href="#" id="passenger_5_a" class="s">删除</a>
					</td>
				<td style="width: 6%"><div id="passenger_5_index">第5位</div></td>
				<td>
				<select name="passenger_5_seat" id="passenger_5_seat" style="width:74px"><option value="1">硬座</option><option value="3" selected="">硬卧</option><option value="4">软卧</option></select> 
				<select id="passenger_5_seat_detail" name="passenger_5_seat_detail" style=""><option value="0">随机</option><option value="2">上铺</option><option value="2">中铺</option><option value="1">下铺</option></select></td><td><select name="passenger_5_ticket" id="passenger_5_ticket"><option value="1" selected="">成人票</option><option value="2">儿童票</option><option value="3">学生票</option><option value="4">残军票</option></select></td>
				<td><input name="passenger_5_name" type="text" id="passenger_5_name" size="12" maxlength="30" class="input_20txt" value=""></td>
				<td><select name="passenger_5_cardtype" id="passenger_5_cardtype">
						
							<option value="1">二代身份证</option>
						
							<option value="2">一代身份证</option>
						
							<option value="C">港澳通行证</option>
						
							<option value="G">台湾通行证</option>
						
							<option value="B">护照</option>
						
				</select></td>
				<td><input type="text" id="passenger_5_cardno" name="passenger_5_cardno" size="20" maxlength="20" style="text-transform: uppercase;" class="input_20txt"></td>
				<td><input type="text" id="passenger_5_mobileno" name="passenger_5_mobileno" size="11" maxlength="20" class="input_20txt"></td>
				<td style="width: 16%"><label> 
							<input type="checkbox" name="checkbox9" id="passenger_5_isSave" value="Y" checked="">    保存到常用联系人
									      
				</label></td>
				
			</tr>
		
	
	
	

	<tr>
		<td colspan="8">
		<font color="#FF0000">*</font>&nbsp;请输入验证码：<input type="text" name="randCode" maxlength="4" tabindex="101" value="" id="rand" style="width:60px;" class="input_20txt">&nbsp;<img border="0" src="/otsweb/passCodeAction.do?rand=randp" id="img_rrand_code" style="vertical-align: text-bottom; cursor: hand;" onclick="this.src=this.src+'&amp;'+Math.random();" title="单击刷新验证码"><a href="#" onclick="javascript:refreshImg();" class="bluetext">&nbsp;&nbsp;<u>看不清，换一张</u>
		</a>
		</td>
		
		
			<td><a href="#" class="add_ticket_passenger" style="display: none;"> <img src="/otsweb/images/er/img_add.gif" height="16" width="16"> <strong>添加1位乘车人</strong>
			</a></td>
		
	</tr>
	
		<tr><td style="border-top:1px dotted #ccc;height:100px;" colspan="9" id="orderCountCell">(因不兼容，实时排队状态查询功能已被系统自动禁用)</td></tr><tr><td style="border-top:1px dotted #ccc;" colspan="9"><ul id="tipScript"><li class="fish_clock" id="countEle" style="font-weight:bold;">等待操作</li><li style="color:green;"><strong>操作信息</strong>：<span>休息中</span></li><li style="color:green;"><strong>最后操作时间</strong>：<span>--</span></li><li style="color:green;" id="safeModeTip"><span>已达安全期，你可以试着提交订单鸟……不过说不定还是会中枪……</span>，已挤进预定页 <span>139.4</span> 秒……</li></ul></td></tr><tr><td colspan="9"><div style="display:;">失败时休息时间：<input type="text" size="4" class="input_20txt" style="text-align:center;" value="3" id="pauseTime">秒 (设置自动重新提交时的时间间隔不得低于1)  </div>安全期时间长度：<input type="text" size="4" class="input_20txt" style="text-align:center;" value="3" id="safeModeTime">秒 (默认为 <span class="defaultSafeModeTime">6</span>秒，可以更改试试，过短可能会导致提交时你被铁道部踹出去……如果发现验证码突然不能显示了，可能需要尝试改大这里的数字喔)  <div><label><input type="checkbox" id="autoStartCommit"> 验证码戳完自动提交，不选就是你自己戳『提交订单』按钮咯——发生异常（提交不了订单等）的请取消勾选此选项唷</label></div><label><input type="checkbox" id="autoDelayInvoke"> 启用安全模式——进入本页10秒钟内的自动提交的自动提交会自动推迟到<span class="defaultSafeModeTime">6</span>秒之后。如果你希望自己掐表，请取消勾选并重试提交，注意的是……一旦时间果断小心被铁道部踹出门哦。</label><div><label><input type="checkbox" id="showHelp"> 显示帮助</label></div></td></tr><tr style="display: none;">
			<td colspan="9" style="border-top: 1px dotted #ccc;">
			 	<ul>
					<li style="color: #000000;">一张有效身份证件同一乘车日期同一车次只能购买一张车票。</li>
					<li style="color: #000000;">购票时可使用的有效身份证件包括：中华人民共和国居民身份证、港澳居民来往内地通行证、台湾居民来往大陆通行证和按规定可使用的有效护照。</li>
					<li style="color: #000000;">购买儿童票时，乘车儿童有有效身份证件的，请填写本人有效身份证件信息。乘车儿童没有有效身份证件的，应使用同行成年人的有效身份证件信息；购票时不受前条限制，<br>但购票后、开车前须办理换票手续方可进站乘车。
					</li>
					<li style="color: #000000;">购买学生票时，须在<a href="/otsweb/passengerAction.do?method=initUsualPassenger">我的常用联系人</a>中登记乘车人的学生详细信息。学生票乘车时间限为每年的暑假6月1日至9月30日、寒假12月1日至3月31日。购票后、开车前，须办理换票手续方可进站乘车。换票时，新生凭录取通知书，毕业生凭学校书面证明，其他凭学生优惠卡。
					</li>
					<li style="color: #000000;">购买残疾军人（伤残警察）优待票的，须在购票后、开车前办理换票手续方可进站乘车。换票时，不符合规定的减价优待条件，没有有效"中华人民共和国残疾军人证"或"中华<br>人民共和国伤残人民警察证"的，不予换票，所购车票按规定办理退票手续。
					</li>
				</ul></td>
		</tr>
	
</tbody></table>
<!-- 证件填写说明弹出框提示 -->

<!-- 姓名填写说明 -->
<div id="tip_info_div_name" class="popdivstyle" style="display: none; position: absolute; top: 172px; left: 442px;">
	<div class="pop_hd">填写说明</div>
	<div class="pop_cd" id="stopContent">
		<ol>
			<li>乘客姓名必须与乘车时所使用的证件上的姓名一致。</li>
			<li>如乘客姓名中包含生僻字可输入汉语拼音代替。例如"李燊"可输入"李shen"。</li>
		</ol>
	</div> 
	<div class="pop_bd" id="stopContent"></div>
</div>
<!-- 证件填写说明 -->
<div id="tip_info_div_cardtype" class="popdivstyle" style="display: none; position: absolute; top: 172px; left: 442px;">
	<div class="pop_hd">填写说明</div>
	<div class="pop_cd" id="stopContent">
		<ol>
			<li>乘客证件号码必须与乘车时所使用的证件上的号码一致。</li>
			<li>请准确、完整填写乘车人证件号码(包括字母、数字)，以免影响您办理换票、乘车等手续。</li>
		</ol>
	</div>
	<div class="pop_bd" id="stopContent"></div>
</div>
<!-- 姓名、证件号码填写说明 -->
<div id="tip_info_div_name_cardtype" class="popdivstyle" style="display: none; position: absolute; top: 172px; left: 442px;">
	<div class="pop_hd">填写说明</div>
	<div class="pop_cd" id="stopContent">
		
		<ol>
			<li>请按乘车时所使用的有效身份证件准确、完整<br>填写乘车人姓名和证件号码，</li>
			<li>如姓名中包含生僻字，可输入汉语拼音代替。<br>例如“李燊”可输入“李shen”。</li>
		</ol>
	</div> 
	<div class="pop_bd" id="stopContent"></div>
</div>

			<div style="display: none;">
				<div class="box-top"></div>
				<div class="box-mid textindent" style="text-align: left;">
					<h1>选择支付方式。</h1>
					<p>
						<input type="radio" name="orderRequest.reserve_flag" value="A" checked="checked" id="reserve_flag_A">

						网上支付<span>须在规定时间内完成支付，快捷进站乘车。</span>
					</p>
					<p>
						<input type="radio" name="orderRequest.reserve_flag" value="B" id="reserve_flag_B">
						网上预订<span>须在规定时间内取票，否则铁路将不予以保留席位。</span>
					</p>
				</div>
				<div class="box-bottom"></div>
			</div>
			<div class="tj_btn">
				<button type="button" onmouseout="this.className='long_button_u'" onmousedown="this.className='long_button_u_down'" onmousemove="this.className='long_button_u_over'" class="long_button_u" onclick="return confirmSelect('confirmPassenger');" id="reChooseButton" tabindex="104">重新选择</button>
				&nbsp;&nbsp;
				<button type="button" onmouseout="this.className='long_button_u'" onmousedown="this.className='long_button_u_down'" onmousemove="this.className='long_button_u_over'" class="long_button_u" onclick="confirmCancel('confirmPassenger');" id="cancelButton" tabindex="103">取消订单</button>
				&nbsp;&nbsp;
				<button type="button" onmouseout="this.className='long_button_u'" onmousedown="this.className='long_button_u_down'" onmousemove="this.className='long_button_u_over'" class="long_button_u" onclick="return submit_form_confirm('confirmPassenger','dc')" tabindex="102">提交订单</button>
			<button class="long_button_u_down" type="button" id="btnAutoSubmit">自动提交</button> <button class="long_button_u_down" type="button" id="btnCancelAuto" style="display:none;">取消自动</button></div>
		</form>