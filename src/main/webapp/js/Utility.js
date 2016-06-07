var tbgUtil = {
		getUrlParam: function(name) {
	          var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	          var r = window.location.search.substr(1).match(reg);
	          if (r) return decodeURI(r[2]); return null;
	      }
};