(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5c2da618"],{"09b5":function(t,a,e){"use strict";e("72a1")},"2f3a":function(t,a,e){"use strict";e.r(a);for(var r=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",[e("a-row",{attrs:{gutter:24}},[e("a-col",{style:{marginBottom:"24px"},attrs:{sm:24,md:12,xl:6}},[e("chart-card",{attrs:{loading:t.loading,title:"总销售额",total:"￥126,560"}},[e("a-tooltip",{attrs:{slot:"action",title:"指标说明"},slot:"action"},[e("a-icon",{attrs:{type:"info-circle-o"}})],1),e("div",[e("trend",{staticStyle:{"margin-right":"16px"},attrs:{flag:"up"}},[e("span",{attrs:{slot:"term"},slot:"term"},[t._v("周同比")]),t._v(" 12% ")]),e("trend",{attrs:{flag:"down"}},[e("span",{attrs:{slot:"term"},slot:"term"},[t._v("日同比")]),t._v(" 11% ")])],1),e("template",{slot:"footer"},[t._v("日均销售额"),e("span",[t._v("￥ 234.56")])])],2)],1),e("a-col",{style:{marginBottom:"24px"},attrs:{sm:24,md:12,xl:6}},[e("chart-card",{attrs:{loading:t.loading,title:"访问量",total:t._f("NumberFormat")(8846)}},[e("a-tooltip",{attrs:{slot:"action",title:"指标说明"},slot:"action"},[e("a-icon",{attrs:{type:"info-circle-o"}})],1),e("div",[e("mini-area")],1),e("template",{slot:"footer"},[t._v("日访问量"),e("span",[t._v(" "+t._s(t._f("NumberFormat")("1234")))])])],2)],1),e("a-col",{style:{marginBottom:"24px"},attrs:{sm:24,md:12,xl:6}},[e("chart-card",{attrs:{loading:t.loading,title:"支付笔数",total:t._f("NumberFormat")(6560)}},[e("a-tooltip",{attrs:{slot:"action",title:"指标说明"},slot:"action"},[e("a-icon",{attrs:{type:"info-circle-o"}})],1),e("div",[e("mini-bar")],1),e("template",{slot:"footer"},[t._v("转化率 "),e("span",[t._v("60%")])])],2)],1),e("a-col",{style:{marginBottom:"24px"},attrs:{sm:24,md:12,xl:6}},[e("chart-card",{attrs:{loading:t.loading,title:"运营活动效果",total:"78%"}},[e("a-tooltip",{attrs:{slot:"action",title:"指标说明"},slot:"action"},[e("a-icon",{attrs:{type:"info-circle-o"}})],1),e("div",[e("mini-progress",{attrs:{color:"rgb(19, 194, 194)",target:80,percentage:78,height:"8px"}})],1),e("template",{slot:"footer"},[e("trend",{staticStyle:{"margin-right":"16px"},attrs:{flag:"down"}},[e("span",{attrs:{slot:"term"},slot:"term"},[t._v("同周比")]),t._v(" 12% ")]),e("trend",{attrs:{flag:"up"}},[e("span",{attrs:{slot:"term"},slot:"term"},[t._v("日环比")]),t._v(" 80% ")])],1)],2)],1)],1),e("a-card",{attrs:{loading:t.loading,bordered:!1,"body-style":{padding:"0"}}},[e("div",{staticClass:"salesCard"},[e("a-tabs",{attrs:{"default-active-key":"1",size:"large","tab-bar-style":{marginBottom:"24px",paddingLeft:"16px"}}},[e("div",{staticClass:"extra-wrapper",attrs:{slot:"tabBarExtraContent"},slot:"tabBarExtraContent"},[e("div",{staticClass:"extra-item"},[e("a",[t._v("今日")]),e("a",[t._v("本周")]),e("a",[t._v("本月")]),e("a",[t._v("本年")])]),e("a-range-picker",{style:{width:"256px"}})],1),e("a-tab-pane",{key:"1",attrs:{loading:"true",tab:"销售额"}},[e("a-row",[e("a-col",{attrs:{xl:16,lg:12,md:12,sm:24,xs:24}},[e("bar",{attrs:{data:t.barData,title:"销售额排行"}})],1),e("a-col",{attrs:{xl:8,lg:12,md:12,sm:24,xs:24}},[e("rank-list",{attrs:{title:"门店销售排行榜",list:t.rankList}})],1)],1)],1),e("a-tab-pane",{key:"2",attrs:{tab:"访问量"}},[e("a-row",[e("a-col",{attrs:{xl:16,lg:12,md:12,sm:24,xs:24}},[e("bar",{attrs:{data:t.barData2,title:"销售额趋势"}})],1),e("a-col",{attrs:{xl:8,lg:12,md:12,sm:24,xs:24}},[e("rank-list",{attrs:{title:"门店销售排行榜",list:t.rankList}})],1)],1)],1)],1)],1)]),e("div",{staticClass:"antd-pro-pages-dashboard-analysis-twoColLayout",class:!t.isMobile&&"desktop"},[e("a-row",{style:{marginTop:"24px"},attrs:{gutter:24,type:"flex"}},[e("a-col",{attrs:{xl:12,lg:24,md:24,sm:24,xs:24}},[e("a-card",{style:{height:"100%"},attrs:{loading:t.loading,bordered:!1,title:"线上热门搜索"}},[e("a-dropdown",{attrs:{slot:"extra",trigger:["click"],placement:"bottomLeft"},slot:"extra"},[e("a",{staticClass:"ant-dropdown-link",attrs:{href:"#"}},[e("a-icon",{attrs:{type:"ellipsis"}})],1),e("a-menu",{attrs:{slot:"overlay"},slot:"overlay"},[e("a-menu-item",[e("a",{attrs:{href:"javascript:;"}},[t._v("操作一")])]),e("a-menu-item",[e("a",{attrs:{href:"javascript:;"}},[t._v("操作二")])])],1)],1),e("a-row",{attrs:{gutter:68}},[e("a-col",{style:{marginBottom:" 24px"},attrs:{xs:24,sm:12}},[e("number-info",{attrs:{total:12321,"sub-total":17.1}},[e("span",{attrs:{slot:"subtitle"},slot:"subtitle"},[e("span",[t._v("搜索用户数")]),e("a-tooltip",{attrs:{slot:"action",title:"指标说明"},slot:"action"},[e("a-icon",{style:{marginLeft:"8px"},attrs:{type:"info-circle-o"}})],1)],1)]),e("div",[e("mini-smooth-area",{style:{height:"45px"},attrs:{dataSource:t.searchUserData,scale:t.searchUserScale}})],1)],1),e("a-col",{style:{marginBottom:" 24px"},attrs:{xs:24,sm:12}},[e("number-info",{attrs:{total:2.7,"sub-total":26.2,status:"down"}},[e("span",{attrs:{slot:"subtitle"},slot:"subtitle"},[e("span",[t._v("人均搜索次数")]),e("a-tooltip",{attrs:{slot:"action",title:"指标说明"},slot:"action"},[e("a-icon",{style:{marginLeft:"8px"},attrs:{type:"info-circle-o"}})],1)],1)]),e("div",[e("mini-smooth-area",{style:{height:"45px"},attrs:{dataSource:t.searchUserData,scale:t.searchUserScale}})],1)],1)],1),e("div",{staticClass:"ant-table-wrapper"},[e("a-table",{attrs:{"row-key":"index",size:"small",columns:t.searchTableColumns,dataSource:t.searchData,pagination:{pageSize:5}},scopedSlots:t._u([{key:"range",fn:function(a,r){return e("span",{},[e("trend",{attrs:{flag:0===r.status?"up":"down"}},[t._v(" "+t._s(a)+"% ")])],1)}}])})],1)],1)],1),e("a-col",{attrs:{xl:12,lg:24,md:24,sm:24,xs:24}},[e("a-card",{staticClass:"antd-pro-pages-dashboard-analysis-salesCard",style:{height:"100%"},attrs:{loading:t.loading,bordered:!1,title:"销售额类别占比"}},[e("div",{staticStyle:{height:"inherit"},attrs:{slot:"extra"},slot:"extra"},[e("span",{staticClass:"dashboard-analysis-iconGroup"},[e("a-dropdown",{attrs:{trigger:["click"],placement:"bottomLeft"}},[e("a-icon",{staticClass:"ant-dropdown-link",attrs:{type:"ellipsis"}}),e("a-menu",{attrs:{slot:"overlay"},slot:"overlay"},[e("a-menu-item",[e("a",{attrs:{href:"javascript:;"}},[t._v("操作一")])]),e("a-menu-item",[e("a",{attrs:{href:"javascript:;"}},[t._v("操作二")])])],1)],1)],1),e("div",{staticClass:"analysis-salesTypeRadio"},[e("a-radio-group",{attrs:{defaultValue:"a"}},[e("a-radio-button",{attrs:{value:"a"}},[t._v("全部渠道")]),e("a-radio-button",{attrs:{value:"b"}},[t._v("线上")]),e("a-radio-button",{attrs:{value:"c"}},[t._v("门店")])],1)],1)]),e("h4",[t._v("销售额")]),e("div",[e("div",[e("v-chart",{attrs:{"force-fit":!0,height:405,data:t.pieData,scale:t.pieScale}},[e("v-tooltip",{attrs:{showTitle:!1,dataKey:"item*percent"}}),e("v-axis"),e("v-legend",{attrs:{dataKey:"item"}}),e("v-pie",{attrs:{position:"percent",color:"item",vStyle:t.pieStyle}}),e("v-coord",{attrs:{type:"theta",radius:.75,innerRadius:.6}})],1)],1)])])],1)],1)],1)],1)},s=[],o=e("c1df"),i=e.n(o),n=e("2af9"),l=e("432b"),c=[],d=[],p=0;p<12;p+=1)c.push({x:"".concat(p+1,"月"),y:Math.floor(1e3*Math.random())+200}),d.push({x:"".concat(p+1,"月"),y:Math.floor(1e3*Math.random())+200});for(var u=[],m=0;m<7;m++)u.push({name:"白鹭岛 "+(m+1)+" 号店",total:1234.56-100*m});for(var f=[],g=0;g<7;g++)f.push({x:i()().add(g,"days").format("YYYY-MM-DD"),y:Math.ceil(10*Math.random())});for(var h=[{dataKey:"x",alias:"时间"},{dataKey:"y",alias:"用户数",min:0,max:10}],v=[{dataIndex:"index",title:"排名",width:90},{dataIndex:"keyword",title:"搜索关键词"},{dataIndex:"count",title:"用户数"},{dataIndex:"range",title:"周涨幅",align:"right",sorter:function(t,a){return t.range-a.range},scopedSlots:{customRender:"range"}}],y=[],b=0;b<50;b+=1)y.push({index:b+1,keyword:"搜索关键词-".concat(b),count:Math.floor(1e3*Math.random()),range:Math.floor(100*Math.random()),status:Math.floor(10*Math.random()%2)});var x=e("7104"),_=[{item:"家用电器",count:32.2},{item:"食用酒水",count:21},{item:"个护健康",count:17},{item:"服饰箱包",count:13},{item:"母婴产品",count:9},{item:"其他",count:7.8}],w=[{dataKey:"percent",min:0,formatter:".0%"}],k=(new x.View).source(_);k.transform({type:"percent",field:"count",dimension:"item",as:"percent"});var M=k.rows,C={name:"Analysis",mixins:[l["a"]],components:{ChartCard:n["b"],MiniArea:n["c"],MiniBar:n["d"],MiniProgress:n["e"],RankList:n["h"],Bar:n["a"],Trend:n["j"],NumberInfo:n["g"],MiniSmoothArea:n["f"]},data:function(){return{loading:!0,rankList:u,searchUserData:f,searchUserScale:h,searchTableColumns:v,searchData:y,barData:c,barData2:d,pieScale:w,pieData:M,sourceData:_,pieStyle:{stroke:"#fff",lineWidth:1}}},created:function(){var t=this;setTimeout((function(){t.loading=!t.loading}),1e3)}},S=C,D=(e("09b5"),e("2877")),T=Object(D["a"])(S,r,s,!1,null,"6ebfe19c",null);a["default"]=T.exports},"432b":function(t,a,e){"use strict";e.d(a,"a",(function(){return o}));var r=e("5530"),s=e("5880"),o={computed:Object(r["a"])(Object(r["a"])({},Object(s["mapState"])({layout:function(t){return t.app.layout},navTheme:function(t){return t.app.theme},primaryColor:function(t){return t.app.color},colorWeak:function(t){return t.app.weak},fixedHeader:function(t){return t.app.fixedHeader},fixedSidebar:function(t){return t.app.fixedSidebar},contentWidth:function(t){return t.app.contentWidth},autoHideHeader:function(t){return t.app.autoHideHeader},isMobile:function(t){return t.app.isMobile},sideCollapsed:function(t){return t.app.sideCollapsed},multiTab:function(t){return t.app.multiTab}})),{},{isTopMenu:function(){return"topmenu"===this.layout}}),methods:{isSideMenu:function(){return!this.isTopMenu}}}},"72a1":function(t,a,e){}}]);