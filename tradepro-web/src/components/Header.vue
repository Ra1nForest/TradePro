<template>
  <div>
    <span>王小虎</span>
    <el-dropdown>
      <i class="el-icon-arrow-down" style="margin: 15px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logout">安全退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>

export default {
  name : "Header",
  methods:{
    toUser() {
      console.log("toUser");
    },
    logout() {
      console.log("logout");
    }
  }
}

</script>

<style>

</style>