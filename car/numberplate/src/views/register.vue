<template>
    <div class="firstdemo">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row type="flex" justify="center">
                <el-col :span="8">
                    <el-form-item label="UserName" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row type="flex" justify="center">
                <el-col :span="8">
                    <el-form-item label="Password" prop="password">
                        <el-input v-model="form.password" type="password"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row type="flex" justify="center">
                <el-col :span="8">
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit('form')">Register Now</el-button>
                         <el-button @click="back">Back</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>
<script>
export default {
    name: "register",
    data() {
        return {
            form: {
                name: "",
                password: ""
            },
            rules:{
                name: [
                    { required: true, message: 'Please input user name', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: 'Please input password', trigger: 'blur' },
                ],
            }
        };
    },
    methods: {
        onSubmit(formName) {
            const _this = this
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    axios.post('http://localhost:8181/api/user', _this.form).then(res => {
                        if(res.data && res.data.message == "success"){
                            _this.$message({
                                message: 'register success',
                                type: 'success',
                                duration: 600,
                                callback: setTimeout(function(){
                                    _this.$router.push({ path: "/index" });
                                },1000)
                            }); 
                        }else{
                            _this.$message.error({
                                message: res.data.message || "register fail",
                                duration: 600
                            });
                        }
                    })

                } else {
                    return false;
                }
            });
        },
        back(){
            this.$router.push({path: "/"});
        }
    }
};
</script>
<style scoped>

</style>