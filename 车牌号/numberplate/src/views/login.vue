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
                        <el-button type="primary" @click="onSubmit('form')">Login</el-button>
                        <el-button @click="onRgister">Register</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>
<script>
export default {
    name: "fisrtdemo",
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
                     axios.post('http://localhost:8181/api/authentication', this.form).then(res => {
                        if(res.data.token){
                            _this.$message({
                                message: 'login success',
                                type: 'success',
                                duration: 600,
                                callback: setTimeout(function(){
                                    _this.$router.push({ path: "/index" });
                                },1000)
                            }); 
                        }else{
                            _this.$message.error({
                                message: res.data.message || "login fail",
                                duration: 600
                            });
                        }
                        
                    })

                } else {
                    return false;
                }
            });
        },
        onRgister(){
            this.$router.push({ path: "/register"})
        }
    }
};
</script>
<style scoped>

</style>