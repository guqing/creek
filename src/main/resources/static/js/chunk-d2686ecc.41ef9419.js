(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-d2686ecc"],{"004c":function(t,e,a){"use strict";a.r(e);var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("page-header-wrapper",{scopedSlots:t._u([{key:"content",fn:function(){return[a("div",{staticClass:"page-header-content"},[a("div",{staticClass:"avatar"},[a("a-avatar",{attrs:{size:"large",src:t.avatar}})],1),a("div",{staticClass:"content"},[a("div",{staticClass:"content-title"},[t._v(" "+t._s(t.timeFix)+"，"+t._s(t.userInfo.nickname)),a("span",{staticClass:"welcome-text"},[t._v("，"+t._s(t.welcome))])]),a("div",[t._v("前端工程师 | 蚂蚁金服 - 某某某事业群 - VUE平台")])])])]},proxy:!0},{key:"extraContent",fn:function(){return[a("div",{staticClass:"extra-content"},[a("div",{staticClass:"stat-item"},[a("a-statistic",{attrs:{title:"项目数",value:56}})],1),a("div",{staticClass:"stat-item"},[a("a-statistic",{attrs:{title:"团队内排名",value:8,suffix:"/ 24"}})],1),a("div",{staticClass:"stat-item"},[a("a-statistic",{attrs:{title:"项目访问",value:2223}})],1)])]},proxy:!0}])},[a("div",[a("a-row",{attrs:{gutter:24}},[a("a-col",{attrs:{xl:24,lg:24,md:24,sm:24,xs:24}},[a("a-card",{staticStyle:{"margin-bottom":"24px"},attrs:{loading:t.loading,bordered:!1,title:"进行中的项目","body-style":{padding:0}}},[a("a",{attrs:{slot:"extra"},slot:"extra"},[t._v("全部项目")]),a("div",t._l(t.projects,(function(e,i){return a("a-card-grid",{key:i,staticClass:"project-card-grid"},[a("a-card",{attrs:{bordered:!1,"body-style":{padding:0}}},[a("a-card-meta",[a("div",{staticClass:"card-title",attrs:{slot:"title"},slot:"title"},[a("a-avatar",{attrs:{size:"small",src:e.cover}}),a("a",[t._v(t._s(e.title))])],1),a("div",{staticClass:"card-description",attrs:{slot:"description"},slot:"description"},[t._v(" "+t._s(e.description)+" ")])]),a("div",{staticClass:"project-item"},[a("a",{attrs:{href:"/#/"}},[t._v("科学搬砖组")]),a("span",{staticClass:"datetime"},[t._v("9小时前")])])],1)],1)})),1)]),a("a-card",{attrs:{loading:t.loading,title:"操作记录",bordered:!1}},[a("a-list",t._l(t.activities,(function(e,i){return a("a-list-item",{key:i},[a("a-list-item-meta",{attrs:{description:e.createTime}},[a("span",{attrs:{slot:"title"},slot:"title"},[t._v(t._s(e.operation))])]),a("ellipsis",{attrs:{length:35,tooltip:""}},[t._v(t._s(e.username))])],1)})),1),a("a-pagination",{staticStyle:{"text-align":"right"},attrs:{pageSize:t.pagination.pageSize,total:t.pagination.total},on:{change:t.handleLogPageChange},model:{value:t.pagination.current,callback:function(e){t.$set(t.pagination,"current",e)},expression:"pagination.current"}})],1)],1)],1)],1)])},n=[],r=a("5530"),s=a("ca00"),o=a("5880"),c=a("c4db"),l=a("c0d2"),u=a("59e4"),d={name:"Workplace",components:{PageHeaderWrapper:l["b"],Ellipsis:c["a"]},data:function(){return{timeFix:Object(s["b"])(),avatar:"",user:{},projects:[],loading:!1,activities:[],teams:[],pagination:{current:1,pageSize:5,total:0}}},computed:Object(r["a"])(Object(r["a"])({},Object(o["mapState"])({nickname:function(t){return t.user.nickname},welcome:function(t){return t.user.welcome}})),{},{currentUser:function(){return{name:this.userInfo.username,avatar:this.avatar}},userInfo:function(){return this.$store.getters.userInfo}}),created:function(){this.user=this.userInfo,this.avatar=this.userInfo.avatar,this.handleListActivity()},methods:{handleLogPageChange:function(t){this.pagination.current=t,this.handleListActivity()},getProjects:function(){},handleListActivity:function(){var t=this,e={current:this.pagination.current,pageSize:this.pagination.pageSize};u["a"].list(e).then((function(e){t.activities=e.data.list,t.pagination.total=e.data.total}))},getTeams:function(){}}},p=d,g=(a("e74d"),a("2877")),v=Object(g["a"])(p,i,n,!1,null,"04e1712a",null);e["default"]=v.exports},"59e4":function(t,e,a){"use strict";var i=a("b775"),n={list:function(t){return Object(i["b"])({url:"/log/action",method:"get",params:t})}};e["a"]=n},b596:function(t,e,a){},c4db:function(t,e,a){"use strict";a("a9e3"),a("a15b"),a("d81d");var i,n,r=a("f933"),s=a("d988"),o={name:"Ellipsis",components:{Tooltip:r["a"]},props:{prefixCls:{type:String,default:"ant-pro-ellipsis"},tooltip:{type:Boolean},length:{type:Number,required:!0},lines:{type:Number,default:1},fullWidthRecognition:{type:Boolean,default:!1}},methods:{getStrDom:function(t,e){var a=this.$createElement;return a("span",[Object(s["a"])(t,this.length)+(e>this.length?"...":"")])},getTooltip:function(t,e){var a=this.$createElement;return a(r["a"],[a("template",{slot:"title"},[t]),this.getStrDom(t,e)])}},render:function(){var t=this.$props,e=t.tooltip,a=t.length,i=this.$slots.default.map((function(t){return t.text})).join(""),n=Object(s["c"])(i),r=e&&n>a?this.getTooltip(i,n):this.getStrDom(i,n);return r}},c=o,l=a("2877"),u=Object(l["a"])(c,i,n,!1,null,null,null),d=u.exports;e["a"]=d},d988:function(t,e,a){"use strict";a.d(e,"b",(function(){return i})),a.d(e,"c",(function(){return n})),a.d(e,"a",(function(){return r}));a("4de4"),a("d3b7"),a("498a"),a("ac1f"),a("1276");function i(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:[];return t.filter((function(t){return t.tag||t.text&&""!==t.text.trim()}))}var n=function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"";return t.split("").reduce((function(t,e){var a=e.charCodeAt(0);return a>=0&&a<=128?t+1:t+2}),0)},r=function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"",e=arguments.length>1?arguments[1]:void 0,a=0;return t.split("").reduce((function(t,i){var n=i.charCodeAt(0);return a+=n>=0&&n<=128?1:2,a<=e?t+i:t}),"")}},e74d:function(t,e,a){"use strict";a("b596")}}]);