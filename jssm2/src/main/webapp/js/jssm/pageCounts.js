
	
	function pageCounts(data){
		var tag=0;
		var count = 0;
		var index = 0;
//		var array_page = new Array();
//		var array_data = new Array();
		var array_total = new Array();
		var size = data.length;
//		var k = 0;
//		var json = "";
//		var rows = "";
		var i = 0;
		
		for(;i<size;i++){
			if(i==0){
				array_total[index]=data[0];
				tag = data[0].mlid;
				if(data[i].pic_ewm){
					count ++;
					array_total[index].page = count;
				}
			}else if(data[i].mlid != tag){
				if(array_total[index].page != count && data[i-1].pic_ewm){
					var oldpage = array_total[index].page;
					array_total[index].page = oldpage + "-" + count;
				}
				index++;
				array_total[index]=data[i];
				tag = data[i].mlid;
				if(data[i].pic_ewm){
					count ++;
					array_total[index].page = count;
				}
			}else{
				count++;
			}
		}
		if(data[i-1].pic_ewm){
			if(array_total[index].page != count){
				var oldpage = array_total[index].page;
				array_total[index].page = oldpage + "-" + count;
			}
		}
		
		return array_total;
};
