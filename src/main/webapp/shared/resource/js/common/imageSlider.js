/**
 * jssor image slider
 * Usage : in $(window).load() event
 * 		 : selector must have an ID
 * 			$("#slider").imageSlider({
 * 				width:1000,								// [mandatory : slider div width]
 * 				height:500,								// [mandatory : slider div height]
 * 				cursor:pointer,							// [optional : cursor]
 * 				thumbnail:true							// [optional : true : displays thumbnail, [default:false]]
 * 				thumbnailHeight:100,					// [optional : height of thumbnail if thumbnail is true, [default:100]]
 * 				thumbType:"image"						// [optional : thumbnail type ([image] / bullet)]
 * 				thumbnailTheme:1,						// [optional : thumbnail theme number (1, 2, 3, 4, 5, 6)]
 * 				thumbSpacingX:0,						// [optional : thumbnail spacingX]
 * 				thumbSpacingY:0,						// [optional : thumbnail spacingY]
 * 				bulletTheme:1,							// [optional : bullet theme number (1, 2, 3, 4, 5, 6, 7, 8, 9, 10)]
 * 				arrow:true,								// [optional : true : displays left/right arrows, [default:true]]
 * 				arrowTheme:1,							// [optional : arrow theme number (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)]
 * 				autoSlide:true							// [optional : play slide automatically, [default:false]]
 * 			});
 */
/*
		var sliderOptions = {
			$AutoPlay:1,
			$DragOrientation:3,							//[Optional] Orientation to drag slide, 0 no drag, 1 horizental, 2 vertical, 3 either, default value is 1 (Note that the $DragOrientation should be the same as $PlayOrientation when $Cols is greater than 1, or parking position is not 0)
			$SlideDuration:500,							//[Optional] Specifies default duration (swipe) for slide in milliseconds, default value is 500
			$SlideshowOptions: {
				$Class:$JssorSlideshowRunner$,
				$Transitions:sliderTransitions,
				$TransitionsOrder:1
			},
			$ArrowNavigatorOptions: {					//[Optional] Options to specify and enable arrow navigator or not
				$Class:$JssorArrowNavigator$,			//[Requried] Class to create arrow navigator instance
				$ChanceToShow:2,						//[Required] 0 Never, 1 Mouse Over, 2 Always
				$AutoCenter:2,							//[Optional] Auto center arrows in parent container, 0 No, 1 Horizontal, 2 Vertical, 3 Both, default value is 0
				$Steps:1								//[Optional] Steps to go for each navigation request, default value is 1
			},
			$ThumbnailNavigatorOptions: {				//[Optional] Options to specify and enable thumbnail navigator or not
				$Class:$JssorThumbnailNavigator$,		//[Required] Class to create thumbnail navigator instance
				$ChanceToShow:2,						//[Required] 0 Never, 1 Mouse Over, 2 Always
				$ActionMode:1,							//[Optional] 0 None, 1 act by click, 2 act by mouse hover, 3 both, default value is 1
				$Cols:5,								//[Optional] Number of pieces to display, default value is 1
				$Align:400								//[Optional] The offset position to park thumbnail
				$Orientation:2,
				$SpacingX:5,							//[Optional] Horizontal space between each thumbnail in pixel, default value is 0
				$SpacingY:5								//[Optional] Horizontal space between each thumbnail in pixel, default value is 0
			},
			$BulletNavigatorOptions: {					//[Optional] Options to specify and enable navigator or not
				$Class:$JssorBulletNavigator$,			//[Required] Class to create navigator instance
				$ChanceToShow:2,						//[Required] 0 Never, 1 Mouse Over, 2 Always
				$AutoCenter:1,							//[Optional] Auto center navigator in parent container, 0 None, 1 Horizontal, 2 Vertical, 3 Both, default value is 0
				$Steps:1,								//[Optional] Steps to go for each navigation request, default value is 1
				$Rows:1,								//[Optional] Specify lanes to arrange items, default value is 1
				$SpacingX:10,							//[Optional] Horizontal space between each item in pixel, default value is 0
				$SpacingY:10,							//[Optional] Vertical space between each item in pixel, default value is 0
				$Orientation:1							//[Optional] The orientation of the navigator, 1 horizontal, 2 vertical, default value is 1
			}
		};
*/
(function($) {
	$.fn.imageSlider = function(options) {
		return this.each(function() {
			if (options.width == null || options.width <= 0) {throw new Error("Width" + com.message.mandatory); return;}
			if (options.height == null || options.height <= 0) {throw new Error("Height" + com.message.mandatory); return;}

			/*!
			 * Default values
			 */
			var cursor = options.cursor || "move",
				thumbnail = options.thumbnail || false,
				thumbType = options.thumbType || "image",
				thumbnailTheme = options.thumbnailTheme || "1",
				bulletTheme = options.bulletTheme || "1",
				arrow = options.arrow || true,
				arrowTheme = options.arrowTheme || "1",
				slideDuration = 500;
			var html = "", maxWidth = 2000, thumbnailHeight = 0, containerHeight = 0, orientation = 2, cols = 1, spacingX = 0, spacingY = 0;

			if (thumbnail && thumbType == "image") {
				if (thumbnailTheme == "5" || thumbnailTheme == "6") {
				} else {
					thumbnailHeight = options.thumbnailHeight || 80;
					orientation = 1;
					cols = 5;
					spacingX = options.thumbSpacingX || 0;
					spacingY = options.thumbSpacingY || 0;
				}
			}

			var sliderTransitions = [
				{$Duration:1200,x:0.3,$During:{$Left:[0.3,0.7]},$Easing:{$Left:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,x:-0.3,$SlideOut:true,$Easing:{$Left:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,x:-0.3,$During:{$Left:[0.3,0.7]},$Easing:{$Left:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,x:0.3,$SlideOut:true,$Easing:{$Left:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,y:0.3,$During:{$Top:[0.3,0.7]},$Easing:{$Top:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,y:-0.3,$SlideOut:true,$Easing:{$Top:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,y:-0.3,$During:{$Top:[0.3,0.7]},$Easing:{$Top:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,y:0.3,$SlideOut:true,$Easing:{$Top:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,x:0.3,$Cols:2,$During:{$Left:[0.3,0.7]},$ChessMode:{$Column:3},$Easing:{$Left:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,x:0.3,$Cols:2,$SlideOut:true,$ChessMode:{$Column:3},$Easing:{$Left:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,y:0.3,$Rows:2,$During:{$Top:[0.3,0.7]},$ChessMode:{$Row:12},$Easing:{$Top:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,y:0.3,$Rows:2,$SlideOut:true,$ChessMode:{$Row:12},$Easing:{$Top:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,y:0.3,$Cols:2,$During:{$Top:[0.3,0.7]},$ChessMode:{$Column:12},$Easing:{$Top:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,y:-0.3,$Cols:2,$SlideOut:true,$ChessMode:{$Column:12},$Easing:{$Top:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,x:0.3,$Rows:2,$During:{$Left:[0.3,0.7]},$ChessMode:{$Row:3},$Easing:{$Left:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,x:-0.3,$Rows:2,$SlideOut:true,$ChessMode:{$Row:3},$Easing:{$Left:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,x:0.3,y:0.3,$Cols:2,$Rows:2,$During:{$Left:[0.3,0.7],$Top:[0.3,0.7]},$ChessMode:{$Column:3,$Row:12},$Easing:{$Left:$Jease$.$InCubic,$Top:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,x:0.3,y:0.3,$Cols:2,$Rows:2,$During:{$Left:[0.3,0.7],$Top:[0.3,0.7]},$SlideOut:true,$ChessMode:{$Column:3,$Row:12},$Easing:{$Left:$Jease$.$InCubic,$Top:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,$Delay:20,$Clip:3,$Assembly:260,$Easing:{$Clip:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,$Delay:20,$Clip:3,$SlideOut:true,$Assembly:260,$Easing:{$Clip:$Jease$.$OutCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,$Delay:20,$Clip:12,$Assembly:260,$Easing:{$Clip:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
				{$Duration:1200,$Delay:20,$Clip:12,$SlideOut:true,$Assembly:260,$Easing:{$Clip:$Jease$.$OutCubic,$Opacity:$Jease$.$Linear},$Opacity:2}
			];
			var sliderOptions = {
				$AutoPlay:(options.autoSlide) ? 1 : 0,
				$DragOrientation:3,
				$SlideDuration:slideDuration,
				$SlideshowOptions:{
					$Class:$JssorSlideshowRunner$,
					$Transitions:sliderTransitions,
					$TransitionsOrder:1
				},
				$ArrowNavigatorOptions:{
					$Class: $JssorArrowNavigator$,
					$ChanceToShow:2,
					$AutoCenter:2,
					$Steps:1
				},
				$ThumbnailNavigatorOptions:{
					$Class: $JssorThumbnailNavigator$,
					$ChanceToShow:2,
					$ActionMode:1,
					$SpacingX:spacingX,
					$SpacingY:spacingY,
					$Cols:cols,
					$Orientation:orientation
				},
				$BulletNavigatorOptions: {
					$Class:$JssorBulletNavigator$,
					$ChanceToShow:2,
					$AutoCenter:1,
					$Steps:1,
					$Rows:1,
					$SpacingX:10,
					$SpacingY:10,
					$Orientation:1
				}
			};

//			containerHeight = $.nony.toNumber(options.height - thumbnailHeight);

			html += "<div data-u=\"slides\" style=\"cursor:"+cursor+";position:relative;top:0px;left:0px;width:"+options.width+"px;height:"+options.height+"px;overflow:hidden;\">";
			html += $(this).html();
			html += "</div>";

			if (arrow) {
				html += getArrowThemeHtml(arrowTheme);
			}

			if (thumbnail && thumbType == "image") {
				html += getThumbnailThemeHtml(thumbnailTheme, options.width, thumbnailHeight);
			}

			if (thumbnail && thumbType == "bullet") {
				html += getBulletThemeHtml(bulletTheme);
			}

			$(this).css({
				position:"relative",
				margin:"0 auto",
				top:"0px",
				left:"0px",
				width:""+options.width+"px",
				height:""+options.height+"px",
				overflow:"hidden",
				visibility:"hidden"
			});

			$(this).html(html);

			var jssorSlider = new $JssorSlider$($(this).attr("id"), sliderOptions);
			function scaleSlider() {
				var containerElement = jssorSlider.$Elmt.parentNode;
				var containerWidth = containerElement.clientWidth;

				if (containerWidth) {
					jssorSlider.$ScaleWidth(Math.min(options.width || maxWidth, containerWidth));
				} else {
					window.setTimeout(scaleSlider, 30);
				}
			}

			scaleSlider();

			$(window).bind("resize", scaleSlider);
			$(window).bind("orientationchange", scaleSlider);

		});
	};

	/*!
	 * private mothod
	 */
	getArrowThemeHtml = function(arrowTheme) {
		var html = "";

		if (arrowTheme == "1" || arrowTheme == "2") {
			html += "<div data-u=\"arrowleft\" class=\"arrow"+arrowTheme+"\" style=\"width:25px;height:25px;top:0px;left:10px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<path class=\"a\" d=\"M4800,8000c0,192.6,70.4,359.3,211.1,500l4977.8,4977.8c140.7,140.7,307.4,211.1,500,211.1 c192.6,0,359.3-70.4,500-211.1c140.7-140.7,211.1-307.4,211.1-500V3022.2c0-192.6-70.4-359.2-211.1-500 c-140.7-140.8-307.4-211.1-500-211.1c-192.6,0-359.3,70.3-500,211.1L5011.1,7500C4870.4,7640.7,4800,7807.4,4800,8000L4800,8000z\"></path>";
			html += "</svg>";
			html += "</div>";
			html += "<div data-u=\"arrowright\" class=\"arrow"+arrowTheme+"\" style=\"width:25px;height:25px;top:0px;right:10px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<path class=\"a\" d=\"M11200,8000c0,192.6-70.4,359.3-211.1,500l-4977.8,4977.8c-140.7,140.7-307.4,211.1-500,211.1 s-359.3-70.4-500-211.1c-140.7-140.7-211.1-307.4-211.1-500V3022.2c0-192.6,70.4-359.2,211.1-500c140.7-140.8,307.4-211.1,500-211.1 s359.3,70.3,500,211.1L10988.9,7500C11129.6,7640.7,11200,7807.4,11200,8000L11200,8000z\"></path>";
			html += "</svg>";
			html += "</div>";
		} else if (arrowTheme == "3" || arrowTheme == "4") {
			html += "<div data-u=\"arrowleft\" class=\"arrow"+arrowTheme+"\" style=\"width:50px;height:50px;top:0px;left:20px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<path class=\"a\" d=\"M4037.7,8357.3l5891.8,5891.8c100.6,100.6,219.7,150.9,357.3,150.9s256.7-50.3,357.3-150.9 l1318.1-1318.1c100.6-100.6,150.9-219.7,150.9-357.3c0-137.6-50.3-256.7-150.9-357.3L7745.9,8000l4216.4-4216.4 c100.6-100.6,150.9-219.7,150.9-357.3c0-137.6-50.3-256.7-150.9-357.3l-1318.1-1318.1c-100.6-100.6-219.7-150.9-357.3-150.9 s-256.7,50.3-357.3,150.9L4037.7,7642.7c-100.6,100.6-150.9,219.7-150.9,357.3C3886.8,8137.6,3937.1,8256.7,4037.7,8357.3 L4037.7,8357.3z\"></path>";
			html += "</svg>";
			html += "</div>";
			html += "<div data-u=\"arrowright\" class=\"arrow"+arrowTheme+"\" style=\"width:50px;height:50px;top:0px;right:20px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<path class=\"a\" d=\"M11962.3,8357.3l-5891.8,5891.8c-100.6,100.6-219.7,150.9-357.3,150.9s-256.7-50.3-357.3-150.9 L4037.7,12931c-100.6-100.6-150.9-219.7-150.9-357.3c0-137.6,50.3-256.7,150.9-357.3L8254.1,8000L4037.7,3783.6 c-100.6-100.6-150.9-219.7-150.9-357.3c0-137.6,50.3-256.7,150.9-357.3l1318.1-1318.1c100.6-100.6,219.7-150.9,357.3-150.9 s256.7,50.3,357.3,150.9l5891.8,5891.8c100.6,100.6,150.9,219.7,150.9,357.3C12113.2,8137.6,12062.9,8256.7,11962.3,8357.3 L11962.3,8357.3z\"></path>";
			html += "</svg>";
			html += "</div>";
		} else if (arrowTheme == "5" || arrowTheme == "6") {
			html += "<div data-u=\"arrowleft\" class=\"arrow"+arrowTheme+"\" style=\"width:30px;height:40px;top:0px;left:20px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"2000 0 12000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<path class=\"c\" d=\"M4800,14080h6400c528,0,960-432,960-960V2880c0-528-432-960-960-960H4800c-528,0-960,432-960,960 v10240C3840,13648,4272,14080,4800,14080z\"></path>";
			html += "<path class=\"a\" d=\"M6860.8,8102.7l1693.9,1693.9c28.9,28.9,63.2,43.4,102.7,43.4s73.8-14.5,102.7-43.4l379-379 c28.9-28.9,43.4-63.2,43.4-102.7c0-39.6-14.5-73.8-43.4-102.7L7926.9,8000l1212.2-1212.2c28.9-28.9,43.4-63.2,43.4-102.7 c0-39.6-14.5-73.8-43.4-102.7l-379-379c-28.9-28.9-63.2-43.4-102.7-43.4s-73.8,14.5-102.7,43.4L6860.8,7897.3 c-28.9,28.9-43.4,63.2-43.4,102.7S6831.9,8073.8,6860.8,8102.7L6860.8,8102.7z\"></path>";
			html += "</svg>";
			html += "</div>";
			html += "<div data-u=\"arrowright\" class=\"arrow"+arrowTheme+"\" style=\"width:30px;height:40px;top:0px;right:20px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"2000 0 12000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<path class=\"c\" d=\"M11200,14080H4800c-528,0-960-432-960-960V2880c0-528,432-960,960-960h6400 c528,0,960,432,960,960v10240C12160,13648,11728,14080,11200,14080z\"></path>";
			html += "<path class=\"a\" d=\"M9139.2,8102.7L7445.3,9796.6c-28.9,28.9-63.2,43.4-102.7,43.4c-39.6,0-73.8-14.5-102.7-43.4 l-379-379c-28.9-28.9-43.4-63.2-43.4-102.7c0-39.6,14.5-73.8,43.4-102.7L8073.1,8000L6860.8,6787.8 c-28.9-28.9-43.4-63.2-43.4-102.7c0-39.6,14.5-73.8,43.4-102.7l379-379c28.9-28.9,63.2-43.4,102.7-43.4 c39.6,0,73.8,14.5,102.7,43.4l1693.9,1693.9c28.9,28.9,43.4,63.2,43.4,102.7S9168.1,8073.8,9139.2,8102.7L9139.2,8102.7z\"></path>";
			html += "</svg>";
			html += "</div>";
		} else if (arrowTheme == "7" || arrowTheme == "8") {
			html += "<div data-u=\"arrowleft\" class=\"arrow"+arrowTheme+"\" style=\"width:30px;height:30px;top:0px;left:20px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<polyline class=\"a\" points=\"11040,1920 4960,8000 11040,14080\"></polyline>";
			html += "</svg>";
			html += "</div>";
			html += "<div data-u=\"arrowright\" class=\"arrow"+arrowTheme+"\" style=\"width:30px;height:30px;top:0px;right:20px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<polyline class=\"a\" points=\"4960,1920 11040,8000 4960,14080\"></polyline>";
			html += "</svg>";
			html += "</div>";
		} else if (arrowTheme == "9" || arrowTheme == "10") {
			html += "<div data-u=\"arrowleft\" class=\"arrow"+arrowTheme+"\" style=\"width:24px;height:40px;top:0px;left:-1px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"-199 -3000 9600 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<path class=\"c\" d=\"M-199-2428.1C317.2-2538.7,851.8-2600,1401-2600c4197.3,0,7600,3402.7,7600,7600 s-3402.7,7600-7600,7600c-549.2,0-1083.8-61.3-1600-171.9V-2428.1z\"></path>";
			html += "<polygon class=\"a\" points=\"4806.7,1528.5 4806.7,1528.5 4806.7,2707.8 2691.1,5000 4806.7,7292.2 4806.7,8471.5 4806.7,8471.5 1602,5000\"></polygon>";
			html += "</svg>";
			html += "</div>";
			html += "<div data-u=\"arrowright\" class=\"arrow"+arrowTheme+"\" style=\"width:24px;height:40px;top:0px;right:-1px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"-199 -3000 9600 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<path class=\"c\" d=\"M9401,12428.1c-516.2,110.6-1050.8,171.9-1600,171.9c-4197.3,0-7600-3402.7-7600-7600 s3402.7-7600,7600-7600c549.2,0,1083.8,61.3,1600,171.9V12428.1z\"></path>";
			html += "<polygon class=\"a\" points=\"7401,5000 4196.3,8471.5 4196.3,8471.5 4196.3,7292.2 6311.9,5000 4196.3,2707.8 4196.3,1528.5 4196.3,1528.5\"></polygon>";
			html += "</svg>";
			html += "</div>";
		} else if (arrowTheme == "11" || arrowTheme == "12") {
			html += "<div data-u=\"arrowleft\" class=\"arrow"+arrowTheme+"\" style=\"width:50px;height:50px;top:0px;left:10px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<circle class=\"c\" cx=\"8000\" cy=\"8000\" r=\"5920\"></circle>";
			html += "<polyline class=\"a\" points=\"7777.8,6080 5857.8,8000 7777.8,9920\"></polyline>";
			html += "<line class=\"a\" x1=\"10142.2\" y1=\"8000\" x2=\"5857.8\" y2=\"8000\"></line>";
			html += "</svg>";
			html += "</div>";
			html += "<div data-u=\"arrowright\" class=\"arrow"+arrowTheme+"\" style=\"width:50px;height:50px;top:0px;right:10px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<circle class=\"c\" cx=\"8000\" cy=\"8000\" r=\"5920\"></circle>";
			html += "<polyline class=\"a\" points=\"8222.2,6080 10142.2,8000 8222.2,9920\"></polyline>";
			html += "<line class=\"a\" x1=\"5857.8\" y1=\"8000\" x2=\"10142.2\" y2=\"8000\"></line>";
			html += "</svg>";
			html += "</div>";
		} else {
			html += "<div data-u=\"arrowleft\" class=\"arrow"+arrowTheme+"\" style=\"width:25px;height:25px;top:0px;left:10px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<path class=\"a\" d=\"M4800,8000c0,192.6,70.4,359.3,211.1,500l4977.8,4977.8c140.7,140.7,307.4,211.1,500,211.1 c192.6,0,359.3-70.4,500-211.1c140.7-140.7,211.1-307.4,211.1-500V3022.2c0-192.6-70.4-359.2-211.1-500 c-140.7-140.8-307.4-211.1-500-211.1c-192.6,0-359.3,70.3-500,211.1L5011.1,7500C4870.4,7640.7,4800,7807.4,4800,8000L4800,8000z\"></path>";
			html += "</svg>";
			html += "</div>";
			html += "<div data-u=\"arrowright\" class=\"arrow"+arrowTheme+"\" style=\"width:25px;height:25px;top:0px;right:10px\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<path class=\"a\" d=\"M11200,8000c0,192.6-70.4,359.3-211.1,500l-4977.8,4977.8c-140.7,140.7-307.4,211.1-500,211.1 s-359.3-70.4-500-211.1c-140.7-140.7-211.1-307.4-211.1-500V3022.2c0-192.6,70.4-359.2,211.1-500c140.7-140.8,307.4-211.1,500-211.1 s359.3,70.3,500,211.1L10988.9,7500C11129.6,7640.7,11200,7807.4,11200,8000L11200,8000z\"></path>";
			html += "</svg>";
			html += "</div>";
		}

		return html;
	};

	getThumbnailThemeHtml = function(thumbnailTheme, width, height) {
		var html = "";
		var thumbWidth = 0, thumbHeight = 0, background = "";

		if (thumbnailTheme == "1" || thumbnailTheme == "2") {
			thumbWidth = 200;
			thumbHeight = 80;
			background = (thumbnailTheme == "1") ? "#000000" : "#ffffff";

			html += "<div data-u=\"thumbnavigator\" class=\"thumb"+thumbnailTheme+"\" style=\"position:absolute;left:0px;bottom:0px;width:"+width+"px;height:"+height+"px\" data-autocenter=\"1\" data-scale-bottom=\"0.75\">";
			html += "<div data-u=\"slides\" style=\"background:"+background+"\">";
			html += "<div data-u=\"prototype\" class=\"p\" style=\"width:"+thumbWidth+"px;height:"+thumbHeight+"px\">";
			html += "<div data-u=\"thumbnailtemplate\" class=\"t\"></div>";
			html += "</div>";
			html += "</div>";
			html += "</div>";
		} else if (thumbnailTheme == "3" || thumbnailTheme == "4") {
			thumbWidth = 200;
			thumbHeight = 80;

			html += "<div data-u=\"thumbnavigator\" class=\"thumb"+thumbnailTheme+"\" style=\"position:absolute;left:0px;bottom:0px;width:"+width+"px;height:"+height+"px\" data-autocenter=\"1\" data-scale-bottom=\"0.75\">";
			html += "<div data-u=\"slides\">";
			html += "<div data-u=\"prototype\" class=\"p\" style=\"width:"+thumbWidth+"px;height:"+thumbHeight+"px\">";
			html += "<div data-u=\"thumbnailtemplate\" class=\"t\"></div>";
			html += "</div>";
			html += "</div>";
			html += "</div>";
		} else if (thumbnailTheme == "5") {
			height = 50;

			html += "<div u=\"thumbnavigator\" style=\"position:absolute;left:0px;bottom:0px;width:"+width+"px;height:"+height+"px;color:#FFF;overflow:hidden;cursor:default;background-color:rgba(0,0,0,.5)\">";
			html += "<div u=\"slides\">";
			html += "<div u=\"prototype\" style=\"position:absolute;top:0;left:0;width:"+width+"px;height:"+height+"px\">";
			html += "<div u=\"thumbnailtemplate\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;font-family:verdana;font-weight:normal;line-height:50px;font-size:16px;padding-left:10px;box-sizing:border-box\"></div>";
			html += "</div>";
			html += "</div>";
			html += "</div>";
		} else if (thumbnailTheme == "6") {
			height = 50;

			html += "<div u=\"thumbnavigator\" style=\"position:absolute;left:0px;bottom:0px;width:"+width+"px;height:"+height+"px;color:#000;overflow:hidden;cursor:default;background-color:rgba(255,255,255,.5)\">";
			html += "<div u=\"slides\">";
			html += "<div u=\"prototype\" style=\"position:absolute;top:0;left:0;width:"+width+"px;height:"+height+"px\">";
			html += "<div u=\"thumbnailtemplate\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;font-family:verdana;font-weight:normal;line-height:50px;font-size:16px;padding-left:10px;box-sizing:border-box\"></div>";
			html += "</div>";
			html += "</div>";
			html += "</div>";
		} else {
			thumbWidth = 200;
			thumbHeight = 80;
			background = (thumbnailTheme == "1") ? "#000000" : "#ffffff";

			html += "<div data-u=\"thumbnavigator\" class=\"thumb"+thumbnailTheme+"\" style=\"position:absolute;left:0px;bottom:0px;width:"+width+"px;height:"+height+"px\" data-autocenter=\"1\" data-scale-bottom=\"0.75\">";
			html += "<div data-u=\"slides\" style=\"background:"+background+"\">";
			html += "<div data-u=\"prototype\" class=\"p\" style=\"width:"+thumbWidth+"px;height:"+thumbHeight+"px\">";
			html += "<div data-u=\"thumbnailtemplate\" class=\"t\"></div>";
			html += "</div>";
			html += "</div>";
			html += "</div>";
		}

		return html;
	};

	getBulletThemeHtml = function(bulletTheme) {
		var html = "";

		if (bulletTheme == "1" || bulletTheme == "2") {
			html += "<div data-u=\"navigator\" class=\"bullet"+bulletTheme+"\" style=\"position:absolute;bottom:12px;right:12px\" data-autocenter=\"1\" data-scale=\"0.5\" data-scale-bottom=\"0.75\">";
			html += "<div data-u=\"prototype\" class=\"i\" style=\"width:16px;height:16px\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<circle class=\"b\" cx=\"8000\" cy=\"8000\" r=\"5800\"></circle>";
			html += "</svg>";
			html += "</div>";
			html += "</div>";
		} else if (bulletTheme == "3" || bulletTheme == "4") {
			html += "<div data-u=\"navigator\" class=\"bullet"+bulletTheme+"\" style=\"position:absolute;bottom:12px;right:12px\" data-autocenter=\"1\" data-scale=\"0.5\" data-scale-bottom=\"0.75\">";
			html += "<div data-u=\"prototype\" class=\"i\" style=\"width:16px;height:16px\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<path class=\"b\" d=\"M11400,13800H4600c-1320,0-2400-1080-2400-2400V4600c0-1320,1080-2400,2400-2400h6800 c1320,0,2400,1080,2400,2400v6800C13800,12720,12720,13800,11400,13800z\"></path>";
			html += "</svg>";
			html += "</div>";
			html += "</div>";
		} else if (bulletTheme == "5" || bulletTheme == "6") {
			html += "<div data-u=\"navigator\" class=\"bullet"+bulletTheme+"\" style=\"position:absolute;bottom:12px;right:12px\" data-autocenter=\"1\" data-scale=\"0.5\" data-scale-bottom=\"0.75\">";
			html += "<div data-u=\"prototype\" class=\"i\" style=\"width:16px;height:16px\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<rect class=\"b\" x=\"2200\" y=\"2200\" width=\"11600\" height=\"11600\"></rect>";
			html += "</svg>";
			html += "</div>";
			html += "</div>";
		} else if (bulletTheme == "7" || bulletTheme == "8") {
			html += "<div data-u=\"navigator\" class=\"bullet"+bulletTheme+"\" style=\"position:absolute;bottom:12px;right:12px\" data-autocenter=\"1\" data-scale=\"0.5\" data-scale-bottom=\"0.75\">";
			html += "<div data-u=\"prototype\" class=\"i\" style=\"width:16px;height:16px\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<circle class=\"b\" cx=\"8000\" cy=\"8000\" r=\"5800\"></circle>";
			html += "</svg>";
			html += "</div>";
			html += "</div>";
		} else if (bulletTheme == "9" || bulletTheme == "10") {
			html += "<div data-u=\"navigator\" class=\"bullet"+bulletTheme+"\" style=\"position:absolute;bottom:12px;right:12px\" data-autocenter=\"1\" data-scale=\"0.5\" data-scale-bottom=\"0.75\">";
			html += "<div data-u=\"prototype\" class=\"i\" style=\"width:24px;height:24px;font-size:12px;line-height:24px\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;z-index:-1\">";
			html += "<circle class=\"b\" cx=\"8000\" cy=\"8000\" r=\"6666.7\"></circle>";
			html += "</svg>";
			html += "<div data-u=\"numbertemplate\" class=\"n\"></div>";
			html += "</div>";
			html += "</div>";
		} else {
			html += "<div data-u=\"navigator\" class=\"bullet"+bulletTheme+"\" style=\"position:absolute;bottom:12px;right:12px\" data-autocenter=\"1\" data-scale=\"0.5\" data-scale-bottom=\"0.75\">";
			html += "<div data-u=\"prototype\" class=\"i\" style=\"width:16px;height:16px\">";
			html += "<svg viewBox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
			html += "<circle class=\"b\" cx=\"8000\" cy=\"8000\" r=\"5800\"></circle>";
			html += "</svg>";
			html += "</div>";
			html += "</div>";
		}

		return html;
	};
})(jQuery);