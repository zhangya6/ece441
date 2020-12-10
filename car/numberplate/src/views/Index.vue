<template>
    <el-container>
        <el-header height="200px">
            <el-row>
                <el-col :span="12">
                    <div class="grid-content">
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
                            <el-button size="small" type="primary">点击上传</el-button>
                            <div slot="tip" class="el-upload__tip">图片格式为JPG/PNG/BMP，大小不超过5MB</div>
                        </el-upload>
                        <div>
                            <label>图片地址：</label>
                            <el-input 
                                v-model="imageUrl"
                                style="width:40%;margin:0 auto;">
                            </el-input>
                        </div>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="grid-content">
                        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm"
                            >
                            <el-form-item label="车牌号" prop="number">
                                <el-input v-model="ruleForm.number"></el-input>
                            </el-form-item>
                            <el-form-item label="图片地址" prop="url">
                                <el-input v-model="ruleForm.url"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
                                <el-button @click="resetForm('ruleForm')">重置</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </el-col>
            </el-row>
        </el-header>
        <el-main>
            <el-table
                :data="tableData"
                style="margin-bottom:20px;"
                border>
                <el-table-column
                fixed
                type="index"
                label="序号"
                width="80">
                </el-table-column>
                <el-table-column
                prop="number"
                label="车牌号">
                </el-table-column>
                <el-table-column
                prop="url"
                label="图片地址">
                </el-table-column>
                <el-table-column
                label="操作"
                width="100">
                <template slot-scope="scope">
                    <el-button @click="edit(scope.row)" type="text" size="small">修改</el-button>
                    <el-button @click="delet(scope.row)" type="text" size="small">删除</el-button>
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
            <el-dialog title="修改弹窗" :visible.sync="dialogVisible" width="30%">
                <el-form :model="editForm" :rules="editrules" ref="editForm" label-width="100px" class="demo-ruleForm">
                    <el-form-item label="车牌号" prop="number">
                        <el-input v-model="editForm.number"></el-input>
                    </el-form-item>
                    <el-form-item label="图片地址" prop="url">
                        <el-input v-model="editForm.url"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitEditForm('editForm')">立即修改</el-button>
                        <el-button @click="resetEditForm('editForm')">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </el-main>
    </el-container>
</template>

<script>
import { genUpToken } from '../util/util.js'

export default {
  name: 'Index',
  created(){
      var token;
      var policy = {};
      var bucketName = 'numberplate';
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
  },
  data(){
      return {
          tableData:null,
          total: null,
          ruleForm: {
              number: '',
              url: ''
          },
          rules:{
              number: [
                    { required: true, message: '请输入车牌号', trigger: 'blur' }
              ],
              url: [
                    { required: false, message: '请输入图片地址', trigger: 'blur' },
              ]
          },
          dialogVisible: false,
          editForm: {
              number: '',
              url: '',
              id: null
          },
          editrules:{
              number: [
                    { required: true, message: '请输入车牌号', trigger: 'blur' }
              ],
              url: [
                    { required: false, message: '请输入图片地址', trigger: 'blur' },
              ]
          },
          actionPath: 'http://upload.qiniup.com',//华东地区客户端上传地址
          postData: {token: null},
          imageUrl: null,
          fileList: [],
      }
  },
  methods: {
      refreshTable(){
            axios.get("http://localhost:8181/plate/findAll/1/5").then(res => {
                this.tableData = res.data.content
                this.total = res.data.totalElements
            })
      },
      pageChange(currentPage){
        axios.get(`http://localhost:8181/plate/findAll/${currentPage}/5`).then(res => {
            this.tableData = res.data.content
            this.total = res.data.totalElements
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
                        message: '添加成功！',
                        type: 'success',
                        callback: setTimeout(function(){
                            _this.refreshTable()
                        },1000)
                    });
                }else{
                    _this.$message.error('添加失败！');
                }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      edit(row){
          this.dialogVisible = true;
          this.editForm.id = row.id;
          this.editForm.number = row.number;
          this.editForm.url = row.url;
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
                        message: '修改成功！',
                        type: 'success',
                        callback: setTimeout(function(){
                            _this.refreshTable()
                        },1000)
                    });

                }else{
                    _this.$message.error('修改失败！');
                }
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
      delet(row){
        const _this = this
        axios.delete(`http://localhost:8181/plate/deleteById/${row.id}`).then(res => {
            console.log(res);
            if(res.status == 200){
                _this.$message({
                    message: '删除成功！',
                    type: 'success',
                    callback: setTimeout(function(){
                        _this.refreshTable()
                    },1000) 
                });

            }else{
                _this.$message.error('删除失败！');
            }
        })
      },
      handleAvatarSuccess(res, file) {
        // this.imageUrl = URL.createObjectURL(file.raw);
        this.imageUrl = "http://qisuwo568.hd-bkt.clouddn.com/"+res.key //默认域名地址拼接key就是上传图片的url
        this.fileList.push(this.imageUrl)
        console.log(this.imageUrl)
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/bmp';
        const isLt2M = file.size / 1024 / 1024 < 5;
 
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG/PNG/BMP 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 5MB!');
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
        }
  }
}
</script>

<style scoped>
.upload-demo {
    /* display: flex; */
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
</style>
