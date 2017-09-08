<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"  %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    
    <!-- 此meta用于支持Iphone，Android等移动平台 设置页面比例为1，禁止用户缩放页面 -->
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
   
    <!-- 5、敬告：此处的link绝不可少，否则地图无法显示 -->
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    
    <script src="http://cache.amap.com/lbs/static/es5.min.js"></script> 
	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>

<title>龙游公共自行车站点分布</title>
<style type="text/css">
/* 3、可以使用CSS样式控制地图容器的大小，可以不设置 */
 #container {
	margin:0px;
	height: 100%; 
	}  
</style>

<script type="text/javascript">
//页面加载完成执行
 $(function(){
	showRent();
}); 

//显示页面站点
function showRent(){
	// Jquery发送异步请求
	 $.post("${pageContext.request.contextPath}/LYPosition?"+new Date().getTime(),{method:"requestPosition"},function(data){
		for(var i=0;i<data.length;i++){
			//定义变量
		    var operNum = data[i].rentcount;
			var maxNumber = data[i].restorecount;
			var zoneName = data[i].name;
			 
			//根据在架数目不同显示不同图标
			//低储量时
			if(operNum < 0.2*maxNumber){
				var icon = new AMap.Icon({
    				image: 'images/bike/green_small.png',
    				size: new AMap.Size(32, 32)
    			});
			//高储量时
			}else if(operNum > 0.8*maxNumber){
				var icon = new AMap.Icon({
    				image: 'images/bike/red_small.png',
    				size: new AMap.Size(32, 32)
    		});
			//两者之间
			}else{
				var icon = new AMap.Icon({
    				image: 'images/bike/blue_small.png',
    				size: new AMap.Size(32, 32)
    		});
		}
				
				
		   var lng = data[i].lon;
		   var lat = data[i].lat;
				  
		   marker = new AMap.Marker({
				icon: icon,
				position: [lng,lat],
				offset: new AMap.Pixel(-16,-32),
				title: data[i].number+":"+data[i].name+":总数"+data[i].restorecount+":在架"+data[i].rentcount,
				map: map
			});
  				
  		 //点击查看
	     marker.content =  '<div class="info-title">'+data[i].name+"&nbsp;"+data[i].number+"&nbsp;"+'</div><div class="info-content">'+
             			   '可借:'+data[i].rentcount+'<br/>'+
             			   '可还:'+(data[i].restorecount-data[i].rentcount)+'<br/>'+
             			   '</div>'
         marker.on('click', markerClick);
         marker.emit('click', {target: marker});
	}
		
		//图标点击事件 
	    function markerClick(e) {
	    	infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
	        infoWindow.setContent(e.target.content);
	        infoWindow.open(map, e.target.getPosition());
	      }
	}); 
}
</script>
</head>
<body>
<!-- 2、创建地图容器 -->
<div id="container"></div>

<!--1、 高德JavaScript API入口脚本 -->
<script src="http://webapi.amap.com/maps?v=1.3&key=ee1710c4e180567b17806859073595cc"></script>
<script type="text/javascript">
	/* 4、创建地图 ，还有第5步，添加link*/
	var map = new AMap.Map('container',{
		resizeEnable: true,
		zoom:12,
		center:[119.1664,29.02523] //位置中心点
	});
	
	//定位
    map.plugin('AMap.Geolocation', function () {
        geolocation = new AMap.Geolocation({
            enableHighAccuracy: true,//是否使用高精度定位，默认:true
            timeout: 10000,          //超过10秒后停止定位，默认：无穷大
            maximumAge: 0,           //定位结果缓存0毫秒，默认：0
            convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
            showButton: true,        //显示定位按钮，默认：true
            buttonPosition: 'LB',    //定位按钮停靠位置，默认：'LB'，左下角
            buttonOffset: new AMap.Pixel(50, 50),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
            showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
            showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
            panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
            zoomToAccuracy:false,      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
            markerOptions:'<img src="images/bike/icg.png" >', //定位点Marker的配置，不设置该属性则使用默认Marker样式
           // buttonDom:'<img src="images/bike/scan.png" >' //自定义定位按钮的内容。可支持HTML代码或Dom元素对象，不设置该属性则使用默认按钮样式
        });
        map.addControl(geolocation);
        geolocation.getCurrentPosition();
        AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
        AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
    });
	
</script>

</body>
</html>