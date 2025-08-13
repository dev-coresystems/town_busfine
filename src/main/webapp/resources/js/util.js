let conversion = {
	conTrim(_value){
		if(_value != null && _value != "" && _value != undefined){
			return _value.trim();
		} else {
			return _value;
		}
	},
    //콤마 제거
    conDelCommas(_value){
        return _value.replaceAll(",", "");
    },
    //숫자에 콤마 삽입
    withCommas(_value){
        return _value.toString().replace(/[^0-9]/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    //숫자만 입력
    conNumber(_value){
        return _value.replace(/[^0-9]/g, "");
    },
    conNumberNoLeftZero(_value){
        return _value.toString().replace(/(^0+)/, "").replace(/[^0-9]/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    //숫자와 소숫점만 입력
    conNumberPoint(_value){
        return _value.replace(/[^-\.0-9]/g,'');
    },
}