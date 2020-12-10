<template>
    <div class="mainContainer">
        <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card">
            <el-tab-pane label="Home" name="home">
                <el-row>
                    <el-col :span="12">
                        <div class="grid-content" style="margin-right:10px;">
                            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="Plate" prop="number">
                                    <el-input v-model="ruleForm.number"></el-input>
                                </el-form-item>
                                    <el-form-item>
                                    <el-button type="primary" @click="submitForm('ruleForm')">Create Plate</el-button>
                                </el-form-item>
                            </el-form>
                            <h3>Recognizable Vehicle</h3>
                            <el-table
                                :data="tableData"
                                style="margin-bottom:20px;"
                                border>
                                <el-table-column
                                type="index"
                                label="#"
                                width="30">
                                </el-table-column>
                                <el-table-column
                                prop="number"
                                label="License Plate Number">
                                </el-table-column>
                                <el-table-column
                                prop="count"
                                width="100"
                                label="Frequency">
                                </el-table-column>
                                <el-table-column
                                label="Operation"
                                width="100">
                                <template slot-scope="scope">
                                    <el-button @click="edit(scope.row)" type="text" size="small">edit</el-button>
                                    <el-button @click="delet(scope.row)" type="text" size="small">delete</el-button>
                                </template>
                                </el-table-column>
                            </el-table>
                            <el-pagination
                                background
                                layout="prev, pager, next"
                                :page-size="5"
                                :total="total"
                                @current-change="pageChange">
                            </el-pagination>
                            <el-dialog title="Change" :visible.sync="dialogVisible" width="30%">
                                <el-form :model="editForm" :rules="editrules" ref="editForm" label-width="100px" class="demo-ruleForm">
                                    <el-form-item label="Plate" prop="number">
                                        <el-input v-model="editForm.number"></el-input>
                                    </el-form-item>
                                    <el-form-item label="Frequency" prop="count">
                                        <el-input v-model="editForm.count" disabled></el-input>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="submitEditForm('editForm')">Modify Now</el-button>
                                        <el-button @click="resetEditForm('editForm')">Cancel</el-button>
                                    </el-form-item>
                                </el-form>
                            </el-dialog>
                        </div>
                    </el-col>
                    <el-col :span="12">
                        <div class="grid-content">
                            <el-form :model="imgruleForm" :rules="imgrules" ref="imgruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="Picture" prop="url">
                                    <el-input v-model="imgruleForm.url"></el-input>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" @click="submitimgForm('imgruleForm')">Create Picture</el-button>
                                </el-form-item>
                            </el-form>
                            <h3>Unrecognized Vehicle</h3>
                            <el-table
                                :data="imgtableData"
                                style="margin-bottom:20px;"
                                border>
                                <el-table-column
                                type="index"
                                label="#"
                                width="30">
                                </el-table-column>
                                <el-table-column
                                prop="url"
                                label="Picture Address">
                                </el-table-column>
                                <el-table-column
                                label="Operation"
                                width="100">
                                <template slot-scope="scope">
                                    <el-button @click="imgedit(scope.row)" type="text" size="small">edit</el-button>
                                    <el-button @click="imgdelet(scope.row)" type="text" size="small">delete</el-button>
                                </template>
                                </el-table-column>
                            </el-table>
                            <el-pagination
                                background
                                layout="prev, pager, next"
                                :page-size="5"
                                :total="imgtotal"
                                @current-change="imgpageChange">
                            </el-pagination>
                            <el-dialog title="Change" :visible.sync="imgdialogVisible" width="30%">
                                <el-form :model="imgeditForm" :rules="imgeditrules" ref="imgeditForm" label-width="100px" class="demo-ruleForm">
                                    <el-form-item label="Picture" prop="url">
                                        <el-input v-model="imgeditForm.url"></el-input>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="submitimgEditForm('imgeditForm')">Modify Now</el-button>
                                        <el-button @click="resetimgEditForm('imgeditForm')">Cancel</el-button>
                                    </el-form-item>
                                </el-form>
                            </el-dialog>
                        </div>
                    </el-col>
                </el-row>
            </el-tab-pane>
            <el-tab-pane label="UploadImg" name="uploadImg">
                <el-upload
                    class="upload-demo"
                    style="height:120px;"
                    :action="actionPath"
                    accept="image/jpeg,image/gif,image/png,image/bmp"
                    :data="postData"
                    :before-upload="beforeAvatarUpload"
                    :on-success="handleAvatarSuccess"
                    ref="upload"
                    :file-list="fileList"
                    :show-file-list="true"
                    :on-change="handleChange"
                    :on-remove="handleRemove"
                    >
                    <el-button size="small" type="primary">Click upload</el-button>
                    <div slot="tip" class="el-upload__tip">Picture format is JPG/PNG/BMP，Size no more than 5MB</div>
                </el-upload>
                <div>
                    <label>Picture Address：</label>
                    <el-input 
                        v-model="imageUrl"
                        style="width:40%;margin:0 auto;">
                    </el-input>
                </div>
            </el-tab-pane>
            <el-tab-pane label="Emailing" name="email">
                <el-form :model="emailForm" :rules="emailrules" ref="emailForm" label-width="100px" class="demo-ruleForm" style="width:60%;margin:0 auto;">
                    <el-form-item label="address" prop="email">
                        <el-input v-model="emailForm.email"></el-input>
                    </el-form-item>
                    <el-form-item label="subject" prop="subject">
                        <el-input v-model="emailForm.subject"></el-input>
                    </el-form-item>
                    <el-form-item label="content" prop="message">
                        <el-input type="textarea" v-model="emailForm.message"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="sendEmail('emailForm')">send email</el-button>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import { genUpToken } from '../util/util.js'

export default {
    name: 'Home',
    created(){
        var token;
        var policy = {};
        var bucketName = 'numberplat-oversea';
        var AK ='LrlsJo98Z01RS-6H3XvgAu0AHChenbZCfzO9cl6d';
        var SK = 'TpJhQjAwIDDx3nZU58dc-5_cFl0y8_99PxbWVe03';
        var deadline = Math.round(new Date().getTime() / 1000) + 3600;
        policy.scope = bucketName;
        policy.deadline = deadline;
        token=genUpToken(AK, SK, policy);
        this.postData.token=token;

        axios.get("http://localhost:8181/plate/findAll/1/5").then(res => {
            this.tableData = res.data.content
            this.total = res.data.totalElements
        })
        axios.get("http://localhost:8181/picture/findAll/1/5").then(res => {
            this.imgtableData = res.data.content
            this.imgtotal = res.data.totalElements
        })
    },
    data() {
        var checkEmail = (rule, value, callback) => {
            const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
            if (!value) {
            return callback(new Error('address can not empty'))
            }
            setTimeout(() => {
            if (mailReg.test(value)) {
                callback()
            } else {
                callback(new Error('please input right email address'))
            }
            }, 100)
        }
        return {
            activeName: 'home',
            ruleForm: {
              number: '',
              count: null,
              id: null
            },
            rules:{
                number: [
                        { required: true, message: 'Please enter License Plate', trigger: 'blur' }
                ]
            },
            imgruleForm: {
                url: '',
                id: null
            },
            imgrules:{
                url: [
                        { required: true, message: 'Please enter Picture Address', trigger: 'blur' }
                ]
            },
            tableData:null,
            total: null,
            imgtableData:null,
            imgtotal: null,
            actionPath: 'http://up-as0.qiniup.com',//华东地区客户端上传地址
            postData: {token: null},
            imageUrl: null,
            fileList: [],
            dialogVisible: false,
            editForm: {
                number: '',
                count: null,
                id: null
            },
            editrules:{
                number: [
                        { required: true, message: 'Please enter License Plate', trigger: 'blur' }
                ]
            },
            imgdialogVisible: false,
            imgeditForm: {
                url: '',
                id: null
            },
            imgeditrules:{
                url: [
                        { required: true, message: 'Please enter Picture Address', trigger: 'blur' }
                ]
            },
            emailForm:{
                email: '',
                message: '',
                subject: ''
            },
            emailrules: {
                email: [
                    { required:true, validator: checkEmail, trigger: 'blur' }

                ],
                subject: [
                    { required: true, message: 'Please input email subject', trigger: 'blur' }
                ],
                message: [
                    { required: true, message: 'Please input email content', trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
        handleClick(tab, event) {
            console.log(tab, event);
        },
        refreshTable(){
            axios.get("http://localhost:8181/plate/findAll/1/5").then(res => {
                this.tableData = res.data.content
                this.total = res.data.totalElements
            })
        },
        refreshimgTable(){
            axios.get("http://localhost:8181/picture/findAll/1/5").then(res => {
                this.imgtableData = res.data.content
                this.imgtotal = res.data.totalElements
            })
        },
        submitForm(formName) {
            const _this = this
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    console.log(_this.ruleForm);
                    axios.post('http://localhost:8181/plate/save', this.ruleForm).then(res => {
                        console.log(res);
                        if(res.data == "success"){
                            _this.$message({
                                message: 'Added successfully',
                                type: 'success',
                                callback: setTimeout(function(){
                                    _this.refreshTable()
                                },1000)
                            });
                        }else{
                            _this.$message.error('Add failed');
                        }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        submitimgForm(formName) {
            const _this = this
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    axios.post('http://localhost:8181/picture/save', this.imgruleForm).then(res => {
                        console.log(res);
                        if(res.data == "success"){
                            _this.$message({
                                message: 'Added successfully！',
                                type: 'success',
                                callback: setTimeout(function(){
                                    _this.refreshimgTable()
                                },1000)
                            });
                        }else{
                            _this.$message.error('Add failed');
                        }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        pageChange(currentPage){
            axios.get(`http://localhost:8181/plate/findAll/${currentPage}/5`).then(res => {
                this.tableData = res.data.content
                this.total = res.data.totalElements
            })
        },
        imgpageChange(currentPage){
            axios.get(`http://localhost:8181/picture/findAll/${currentPage}/5`).then(res => {
                this.imgtableData = res.data.content
                this.imgtotal = res.data.totalElements
            })
        },
        edit(row){
            this.dialogVisible = true;
            this.editForm.id = row.id;
            this.editForm.number = row.number;
            this.editForm.count = row.count;
        },
        imgedit(row){
            this.imgdialogVisible = true;
            this.imgeditForm.id = row.id;
            this.imgeditForm.url = row.url;
        },
        delet(row){
            const _this = this
            axios.delete(`http://localhost:8181/plate/deleteById/${row.id}`).then(res => {
                if(res.status == 200){
                    _this.$message({
                        message: 'delete successful！',
                        type: 'success',
                        callback: setTimeout(function(){
                            _this.refreshTable()
                        },1000) 
                    });

                }else{
                    _this.$message.error('delete failed');
                }
            })
        },
        imgdelet(row){
            const _this = this
            axios.delete(`http://localhost:8181/picture/deleteById/${row.id}`).then(res => {
                console.log(res);
                if(res.status == 200){
                    _this.$message({
                        message: 'delete successful！',
                        type: 'success',
                        callback: setTimeout(function(){
                            _this.refreshimgTable()
                        },1000) 
                    });

                }else{
                    _this.$message.error('delete failed');
                }
            })
        },
        submitEditForm(formName) {
            const _this = this
            this.$refs[formName].validate((valid) => {
            if (valid) {
                console.log(_this.editForm);
                axios.put('http://localhost:8181/plate/update', this.editForm).then(res => {
                    console.log(res);
                    if(res.data == "success"){
                        _this.$message({
                            message: 'modify successfully',
                            type: 'success',
                            callback: setTimeout(function(){
                                _this.refreshTable()
                            },1000)
                        });

                    }else{
                        _this.$message.error('modify failed');
                    }
                    this.dialogVisible = false;
                })
            } else {
                console.log('error submit!!');
                return false;
            }
            });
        },
        submitimgEditForm(formName) {
            const _this = this
            this.$refs[formName].validate((valid) => {
            if (valid) {
                axios.put('http://localhost:8181/picture/update', this.imgeditForm).then(res => {
                    console.log(res);
                    if(res.data == "success"){
                        _this.$message({
                            message: 'modify successfully',
                            type: 'success',
                            callback: setTimeout(function(){
                                _this.refreshimgTable()
                            },1000)
                        });

                    }else{
                        _this.$message.error('modify failed');
                    }
                    this.imgdialogVisible = false;
                })
            } else {
                console.log('error submit!!');
                return false;
            }
            });
        },
        resetEditForm(formName) {
            this.dialogVisible = false;
        },
        resetimgEditForm(formName) {
            this.imgdialogVisible = false;
        },
        handleAvatarSuccess(res, file) {
            // this.imageUrl = URL.createObjectURL(file.raw);
            this.imageUrl = "http://numberplate.ga/"+res.key //默认域名地址拼接key就是上传图片的url
            this.fileList.push(this.imageUrl)
            console.log(this.imageUrl)
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/bmp';
            const isLt2M = file.size / 1024 / 1024 < 5;
    
            if (!isJPG) {
            this.$message.error('image style only is JPG/PNG/BMP !');
            }
            if (!isLt2M) {
            this.$message.error('the size of image can not bigger than 5MB!');
            }
            return isJPG && isLt2M;
        },
        handleChange(file, fileList) {
            if (fileList.length > 0) {
                this.fileList = [fileList[fileList.length - 1]]  // 这一步，是 展示最后一次选择的文件
            }
        },
        handleRemove(){
            this.imageUrl = ''
        },
        sendEmail(formName) {
            const _this = this
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    axios.post('http://localhost:8181/plate/email', this.emailForm).then(res => {
                        console.log(res);
                        if(res.data == "success"){
                            _this.$message({
                                message: 'send successfully',
                                type: 'success'
                            });
                        }else{
                            _this.$message.error('send failed');
                        }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
    }
}
</script>

<style scoped>
.mainContainer{
    width: 90%;
    margin: 0 auto;
}
/deep/ .el-list-enter-active,
/deep/ .el-list-leave-active {
    transition: none;
}

/deep/ .el-list-enter,
/deep/ .el-list-leave-active {
    opacity: 0;
}
/deep/ .el-upload-list {
    height: 40px;
}
.el-form-item__label{
    width: auto;
    border:1px solid red;
}
</style>
