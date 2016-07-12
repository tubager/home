var tbgUtil = {
	getUuid: function(){
		var d = new Date();
		return (new Date(d.getTime() - d.getTimezoneOffset()*60*1000)).toJSON().replace(/-/g,"").replace(/:/g,"").replace(".","") + Math.ceil(Math.random()*100);
	},
	
	getToday: function(){
		var d = new Date();
		return (new Date(d.getTime() - d.getTimezoneOffset()*60*1000)).toJSON().split("T")[0];
	},
	
    dateTimeFormat:function(ISODate, formatString){
        formatString = formatString || "year年month月date日 hours时minutes分";
        var date = new Date(ISODate);
        var replacer = {
          year: "" + date.getFullYear(),
          month: "" + (date.getMonth()+1),
          date: "" + date.getDate(),
          hours: "" + date.getHours(),
          minutes: "" + date.getMinutes(),
          seconds: "" + date.getSeconds(),
          milliseconds: "" + date.getMilliseconds()
        };
        return formatString.replace(new RegExp(Object.keys(replacer).join("|"), "g"), function(part){
          return replacer[part];
        });
      },
      
      getUrlParam:function(name) {
          var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
          var r = window.location.search.substr(1).match(reg);
          if (r) return decodeURI(r[2]); return null;
      },
      
      showMessage: function(cmd, msg){
    	  toastr.options = {
			  "closeButton": true,
			  "debug": false,
			  "progressBar": true,
			  "preventDuplicates": false,
			  "positionClass": "toast-bottom-center",
			  "onclick": null,
			  "showDuration": "400",
			  "hideDuration": "1000",
			  "timeOut": "2000",
			  "extendedTimeOut": "1000",
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut"
			};
    	  toastr[cmd](msg);
      }
};