(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-ccc26e66"],{"178f":function(e,t,n){"use strict";var l=n("debe"),a=n.n(l);a.a},"755c":function(e,t,n){"use strict";n.r(t);var l=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"container"},[n("div",{staticClass:"head"},[n("el-button",{attrs:{type:"primary",size:"small"},on:{click:function(t){e.visible=!0}}},[e._v("+AddClient")])],1),e._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticStyle:{width:"100%"},attrs:{data:e.tableData,border:""}},[n("el-table-column",{attrs:{label:"ID",width:"110",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n        "+e._s(t.row.id)+"\n      ")]}}])}),e._v(" "),n("el-table-column",{attrs:{prop:"clientId",label:"客户端ID",width:"150",align:"center"}}),e._v(" "),n("el-table-column",{attrs:{prop:"clientSecret",label:"客户端密钥",width:"130",align:"center"}}),e._v(" "),n("el-table-column",{attrs:{prop:"accessTokenExpire",label:"accessToken时效",width:"150",align:"center"}}),e._v(" "),n("el-table-column",{attrs:{prop:"refreshTokenExpire",label:"refreshToken时效",width:"150",align:"center"}}),e._v(" "),n("el-table-column",{attrs:{label:"启用refreshToken",width:"150",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-switch",{attrs:{"active-color":"#13ce66","inactive-color":"#ff4949"},on:{change:function(n){return e.enableChange(t.row)}},model:{value:t.row.enable,callback:function(n){e.$set(t.row,"enable",n)},expression:"scope.row.enable"}})]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{size:"mini",type:"primary"},on:{click:function(n){return e.handleEdit(t.row)}}},[e._v("编辑")]),e._v(" "),n("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(n){return e.handleDelete(t.row)}}},[e._v("删除")])]}}])})],1),e._v(" "),n("el-pagination",{attrs:{layout:"prev, pager, next","hide-on-single-page":"","page-size":e.pageSize,total:e.total},on:{"current-change":e.currentChange}}),e._v(" "),n("el-dialog",{attrs:{title:"保存客户端",top:"25vh",width:"350px",visible:e.visible,"close-on-click-modal":!1,"show-close":!1,"lock-scroll":!1},on:{"update:visible":function(t){e.visible=t}}},[n("el-form",{ref:"form",attrs:{"label-position":"left",model:e.form,rules:e.rules}},[n("el-form-item",{attrs:{prop:"clientId"}},[n("el-input",{attrs:{placeholder:"输入客户端ID"},model:{value:e.form.clientId,callback:function(t){e.$set(e.form,"clientId",t)},expression:"form.clientId"}})],1),e._v(" "),n("el-form-item",{attrs:{prop:"clientSecret"}},[n("el-input",{attrs:{placeholder:"输入客户端秘钥"},model:{value:e.form.clientSecret,callback:function(t){e.$set(e.form,"clientSecret",t)},expression:"form.clientSecret"}})],1),e._v(" "),n("el-form-item",{attrs:{prop:"accessTokenExpire"}},[n("el-input",{attrs:{type:"number",placeholder:"输入AccessToken时效"},model:{value:e.form.accessTokenExpire,callback:function(t){e.$set(e.form,"accessTokenExpire",t)},expression:"form.accessTokenExpire"}})],1),e._v(" "),n("el-form-item",[n("el-switch",{staticStyle:{display:"block"},attrs:{"active-color":"#13ce66","inactive-color":"#ff4949","active-text":"启用refresh","inactive-text":"禁用refresh"},model:{value:e.form.enable,callback:function(t){e.$set(e.form,"enable",t)},expression:"form.enable"}})],1),e._v(" "),e.form.enable?n("el-form-item",{attrs:{prop:"refreshTokenExpire"}},[n("el-input",{attrs:{type:"number",placeholder:"输入RefreshToken时效"},model:{value:e.form.refreshTokenExpire,callback:function(t){e.$set(e.form,"refreshTokenExpire",t)},expression:"form.refreshTokenExpire"}})],1):e._e()],1),e._v(" "),n("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{attrs:{size:"medium"},on:{click:e.handleClose}},[e._v("取 消")]),e._v(" "),n("el-button",{attrs:{size:"medium",type:"primary"},on:{click:e.saveSubmit}},[e._v("确 定")])],1)],1)],1)},a=[],r=(n("ac6a"),n("b775"));function i(e){return r["a"].get("/client/page",{params:e})}function o(e){return r["a"].post("/client/save",e)}function s(e){return r["a"].delete("/client/delete/"+e)}var c={data:function(){return{tableData:[],pageNum:1,pageSize:10,total:0,loading:!0,visible:!1,form:{clientId:"",clientSecret:"",accessTokenExpire:null,refreshTokenExpire:null,id:null,enableRefreshToken:0,enable:!1},rules:{clientId:[{required:!0,message:"请输入客户端ID",trigger:"blur"}],clientSecret:[{required:!0,message:"请输入客户端秘钥",trigger:"blur"}]}}},mounted:function(){this.loadData()},methods:{handleEdit:function(e){this.form=JSON.parse(JSON.stringify(e)),this.visible=!0},enableChange:function(e){var t=e.enable?1:0,n={id:e.id,enableRefreshToken:t,clientId:e.clientId,clientSecret:e.clientSecret};o(n)},handleDelete:function(e){var t=this;this.$confirm("确定删除该客户端吗","提示",{confirmButtonText:"确定",cancelButtonText:"取消",showClose:!1,type:"warning"}).then((function(){s(e.id).then((function(e){t.$message({message:"删除成功",type:"success"}),t.pageNum=1,t.loadData()}))})).catch((function(){}))},handleClose:function(){this.form.clientId="",this.form.clientSecret="",this.form.accessTokenExpire=null,this.form.refreshTokenExpire=null,this.form.id=null,this.form.enable=!1,this.form.enableRefreshToken=0,this.$refs["form"].clearValidate(),this.visible=!1},saveSubmit:function(){var e=this;this.$refs.form.validate((function(t){t&&e.saveClient()}))},currentChange:function(e){this.pageNum=e,this.loadData()},loadData:function(){var e=this;this.loading=!0;var t={current:this.pageNum,size:this.pageSize};i(t).then((function(t){e.total=t.data.total;var n=t.data.records;n.forEach((function(e){e.enable=!!e.enableRefreshToken})),e.tableData=n,e.loading=!1}),(function(t){console.error(t),e.loading=!1}))},saveClient:function(){var e=this;this.form.enableRefreshToken=this.form.enable?1:0,delete this.form.enable,o(this.form).then((function(t){e.$message({message:"保存成功",type:"success"}),e.handleClose(),e.pageNum=1,e.loadData()}))}}},u=c,f=(n("178f"),n("2877")),d=Object(f["a"])(u,l,a,!1,null,"78bed292",null);t["default"]=d.exports},debe:function(e,t,n){}}]);