;(function ( $, window, document, undefined ) {
	"use strict";
	
	var pluginName = 'bootstrapDatepickr';
    var defaults = {
		date_format: "d/m/Y"
	};
	
	var text = document.getElementById("calendar").value;
			
				this.$el.val(value)=text;
				this.$el.val(value)
	
	// The actual plugin constructor
    function Plugin( element, options ) {
        this.element = element;
        this.options = $.extend( {}, defaults, options) ;
        this._defaults = defaults;
        this._name = pluginName;
		this.settings = {
			'date_object': new Date(),
			'selected_day': '',
            'current_month': new Date().getMonth() + 1,
            'current_year': new Date().getFullYear(),
			'selected_date': '',
			'allowed_formats': ['d', 'do', 'D', 'j', 'l', 'w', 'F', 'm', 'M', 'n', 'U', 'y', 'Y'],
			'month_name': ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
			'date_formatting': {
				'weekdays': {
					'shorthand': ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
					'longhand': ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday','Friday', 'Saturday']
				},
				'months': {
					shorthand: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul','Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
					longhand: ['January', 'February', 'March', 'April', 'May', 'June', 'July','August', 'September', 'October', 'November', 'December']
				}
			}
        };

		this.init();
    }
	
	// Avoid Plugin.prototype conflicts
	$.extend(Plugin.prototype, {
		init: function () {
			var plugin = this;
			var element = plugin.element;
			
			// make input-group-addon's clickable too
			if($(element).parent().hasClass("input-group")){
				$(element).prev().addClass('addonclick_id-' + $(element).attr('id'));
			}
			
			// hook up our event bindings
			this.bindEvents();
			
			// init the calendar 
			this.init_cal(plugin, element, plugin.settings.current_month, plugin.settings.current_year);
		},
		
		bindEvents: function() {
			var plugin = this;
			var element = plugin.element;
			
			// trap click events
			$(element).on("click", plugin, this.wrapper_clicked);
			$('.addonclick_id-' + $(element).attr('id')).on("click",plugin , this.addon_clicked);
		},
		
		// we init the calendar wrappers so we can hook into the next/prev month links
		init_cal: function(plugin, element, cal_month, cal_year) {
			var settings = plugin.settings;
			
			var t = '<div class="bootstrapDatepickr-cal cal_id-' + $(element).attr('id') + ' bootstrapDatepickr-hidden">';
			t += '<table id="bootstrapDatepickr-table" class="table table-bordered table_id-' + $(element).attr('id') + '" cols="7">';
			t += '<tr class="bootstrapDatepickr-header">';
			t += '<th class="bootstrapDatepickr-prev-month prev_id-' + $(element).attr('id') + '" colspan="1">&laquo;</th>';
			t += '<th colspan="5" class="bootstrapDatepickr-month month_id-' + $(element).attr('id') + '">' + settings.month_name[cal_month - 1] + '  ' + cal_year + '</th>';
			t += '<th class="bootstrapDatepickr-next-month next_id-' + $(element).attr('id') + '" colspan="1">&raquo;</th></tr>';
			t += '<tbody id="bootstrapDatepickr-body-' + $(element).attr('id') + '">';
			t += '</tbody></table></div>';
			
			// insert into DOM
			$(element).after(t);
			
			// generate calendar body
			this.generate_calendar(plugin, settings.current_month, settings.current_year);
		},		
		
		addon_clicked: function(e)
		{
			// toggle the calendar on input-group-addon click
			var plugin = e.data;
			$(".cal_id-" + $(plugin.element).attr('id')).toggleClass("bootstrapDatepickr-hidden");
		},
		
		wrapper_clicked: function(e)
		{
			// get settings
			var plugin = e.data;
			var settings = plugin.settings;
			
			// returns the date when a day is clicked
			$('body').unbind().on('click', '.day_id-' + $(this).attr('id'), function () {
				if($(this).html() != "&nbsp;"){
					settings.selected_day = $(this).html();
					var formatted_date = formatDate();
					settings.selected_date = formatted_date;
					
					// set the input value
					$("#" + $(plugin.element).attr('id')).val(formatted_date);
	
					// show/hide the wrapper element
					$(".cal_id-" + $(plugin.element).attr('id')).addClass("bootstrapDatepickr-hidden");
				}
			});
			
			// next month click event
			$('body').on('click', '.next_id-' + $(this).attr('id'), function () {
				// adjust the near if next is pressed in december		
				if(settings.current_month === 12){
					settings.current_year++;
					settings.current_month = 1;
				}else{
					settings.current_month++;
				}

				// generate the new calendar
				plugin.generate_calendar(plugin, settings.current_month, settings.current_year);
				
				// update the month text
				$(".month_id-" + $(plugin.element).attr('id')).html(settings.month_name[settings.current_month - 1] + " - " + settings.current_year);
			});
			
			// previous month click event
			$('body').on('click', '.prev_id-' + $(this).attr('id'), function () {
				// adjust the near if prev is pressed in Jan
				if(settings.current_month == 1){
					settings.current_year = Number(settings.current_year) - 1;
					settings.current_month = 12;
				}else{
					settings.current_month = Number(settings.current_month) - 1;
				}
				
				// generate the new calendar
				plugin.generate_calendar(plugin, settings.current_month, settings.current_year);
				
				// update the month text
				$(".month_id-" + $(plugin.element).attr('id')).html(settings.month_name[settings.current_month - 1] + " - " + settings.current_year);
			});
			
			// show/hide the wrapper element
			$(".cal_id-" + $(this).attr('id')).toggleClass("bootstrapDatepickr-hidden");;
			
			// toggles the 'bootstrapDatepickr-selected_date' class for the already selected day
			plugin.highlight_selected_day(plugin);
			
			// formats the date using the default/supplied options date
			function formatDate () {
				var currentTimestamp = new Date(plugin.settings.current_year, plugin.settings.current_month - 1, plugin.settings.selected_day);
				plugin.settings.date_object = currentTimestamp;
				var dateFormat = plugin.options.date_format,
					dateObj = new Date(currentTimestamp.getTime()),
					formats = {
						d: function () {
							var day = formats.j();
							return (day < 10) ? '0' + day : day;
						},
						do: function () {
							var day = formats.j();
							return getGetOrdinal(day);
						},
						D: function () {
							return plugin.settings.date_formatting.weekdays.shorthand[formats.w()];
						},
						j: function () {
							return dateObj.getDate();
						},
						l: function () {
							return plugin.settings.date_formatting.weekdays.longhand[formats.w()];
						},
						w: function () {
							return dateObj.getDay();
						},
						F: function () {
							return monthToStr(formats.n() - 1, false);
						},
						m: function () {
							var month = formats.n();
							return (month < 10) ? '0' + month : month;
						},
						M: function () {
							return monthToStr(formats.n() - 1, true);
						},
						n: function () {
							return dateObj.getMonth() + 1;
						},
						U: function () {
							return dateObj.getTime() / 1000;
						},
						y: function () {
							return String(formats.Y()).substring(2);
						},
						Y: function () {
							return dateObj.getFullYear();
						}
					};
					var formatPieces = "";
					var splitChar = "";
					if(dateFormat.indexOf("/") > -1){
						formatPieces = dateFormat.split('/');
						splitChar = "/";
					}else if(dateFormat.indexOf("-") > -1){
						formatPieces = dateFormat.split('-');
						splitChar = "-";
					}else{
						formatPieces = dateFormat.split(' ');
						splitChar = " ";
					}
				
				// build formatted date string 
				$.each(formatPieces, function( index, formatPiece ) {
					// strip non alpha from format piece
					formatPiece = formatPiece.replace(/[\W_]+/g,"").trim();
					
					// check the formatPiece is in the list of allowed formatting values. This allows for punctuation (comma etc) in the date format
					if(plugin.settings.allowed_formats.indexOf(formatPiece) > -1){
						dateFormat = dateFormat.replace(formatPiece, formats[formatPiece]);
					}
				});
			
				return dateFormat;
			};
			
			// borrowed from Spotify
			function getGetOrdinal(n) {
				var s=["th","st","nd","rd"],
					v=n%100;
				return n+(s[(v-20)%10]||s[v]||s[0]);
			}
		
			function monthToStr (date, shorthand) {
				if (shorthand === true) {
					return plugin.settings.date_formatting.months.shorthand[date];
				}
		
				return plugin.settings.date_formatting.months.longhand[date];
			};
			
			function pad(n, width, z) {
				z = z || '0';
				n = n + '';
				return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
			}
			
			// splits up the class to get the ID
			function get_id_from_class(full_class){
				var id_class = full_class.split(' ')[1];
				var id_split = id_class.split('-');
				return id_split[id_split.length-1];
			}
		},
		
		// toggles the 'bootstrapDatepickr-selected_date' class for the already selected day
		highlight_selected_day: function(plugin){
			if($(plugin.element).val() != ""){
				if(plugin.settings.selected_date != ""){
					// check selected year is currently showing
					if(plugin.settings.current_year == plugin.settings.date_object.getFullYear()){
						// check selected month is currently showing
						if(plugin.settings.current_month == plugin.settings.date_object.getMonth() + 1){
							$(".day_id-" + $(plugin.element).attr('id')).each(function(index) {
								if(plugin.settings.selected_day == $(this).text()){
									$(this).addClass('bootstrapDatepickr-selected_date');
								}else{
									$(this).removeClass('bootstrapDatepickr-selected_date');
								}
							});
						}
					}
				}
			}
		},
		
		// generate the day rows of the calendar
		generate_calendar: function(plugin, cal_month, cal_year) {
			var element = plugin.element;
			
			var month_days = [31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
			
			var todaydate = new Date();
			
			var dte_obe = new Date(cal_year, cal_month - 1, 1); //DD replaced line to fix date bug when current day is 31st
			dte_obe.od = dte_obe.getDay() + 1; //DD replaced line to fix date bug when current day is 31st
			
			var scanfortoday = (cal_year == todaydate.getFullYear() && cal_month == todaydate.getMonth() + 1) ? todaydate.getDate() : 0 //DD added
		
			month_days[1] = (((dte_obe.getFullYear() % 100 != 0) && (dte_obe.getFullYear() % 4 == 0)) || (dte_obe.getFullYear() % 400 == 0)) ? 29 : 28;
			
			// remove all the rows
			$('.row_id-'+ $(element).attr('id')).remove();
			
			var t = '<tr class="bootstrapDatepickr-row row_id-'+ $(element).attr('id')+'">';
			for (var s = 0; s < 7; s++)t += '<td class="bootstrapDatepickr-days">' + "SMTWTFS".substr(s, 1) + '</td>';
			t += '</tr><tr class="bootstrapDatepickr-row row_id-'+ $(element).attr('id')+'">';
			for (var i = 1; i <= 42; i++) {
				var today_class = "";
				var x = ((i - dte_obe.od >= 0) && (i - dte_obe.od < month_days[cal_month - 1])) ? i - dte_obe.od + 1 : '&nbsp;';
				if (x == scanfortoday){ //DD added
					today_class = 'bootstrapDatepickr-today'; //DD added
				}
				
				t += '<td class="bootstrapDatepickr-day day_id-'+ $(element).attr('id') +' '+ today_class + '">' + x + '</td>';
				if (((i) % 7 == 0) && (i < 36)) t += '</tr><tr class="bootstrapDatepickr-row row_id-'+ $(element).attr('id')+'">';
			}
			
			t += '</tr></table></div>';
			
			// append all the rows
			$('#bootstrapDatepickr-body-' + $(element).attr('id')).append(t);
			
			// loop rows and remove any where all td's are blank
			$('.row_id-'+ $(element).attr('id')).each(function() {
				if($(this).text().trim() === ""){
					$(this).remove();
				}
			});
			
			// here we check for bootstrap input-group tags and ensure the calendar wrapper sits after the input group to 
			// ensure the "add-on" remains the correct height
			if($(element).parent().hasClass("input-group")){
				$('.cal_id-' + $(element).attr('id')).detach().insertAfter($(element).parent());
				$('.cal_id-' + $(element).attr('id')).css("left", $(element).parent().position().left);
				$('.cal_id-' + $(element).attr('id')).css("width", $(element).parent().outerWidth());
			}else{
				$('.cal_id-' + $(element).attr('id')).css("left", $(element).position().left);
				$('.cal_id-' + $(element).attr('id')).css("width", $(element).outerWidth());
			}
			
			// position the wrapper in relation to the triggering element
			$('.cal_id-' + $(element).attr('id')).css("top", $(element).position().top + $(element).outerHeight());
			
			// toggles the 'bootstrapDatepickr-selected_date' class for the already selected day
			this.highlight_selected_day(plugin);
		}	
	});
	
	$.fn[pluginName] = function (options) {
		return this.each(function() {
			if ( !$.data( this, "plugin_" + pluginName ) ) {
					$.data( this, "plugin_" + pluginName, new Plugin( this, options ) );
			}
		});
	};
	
})(jQuery, window, document);