<template>
  <div style="overflow:auto;height: 100vh">
    <el-container style="height: 100vh; border: 1px solid #eee">
      <el-aside style="width:15%;min-width: 20vh; background-color: rgb(238, 241, 246)">
        <Aside></Aside>
      </el-aside>
      <div style="height: 100%;width: 100%">
        <el-container>
          <el-header style="text-align: right; font-size: 12px">
            <Header></Header>
          </el-header>
          <el-main>
            <Main></Main>
          </el-main>
        </el-container>
      </div>
    </el-container>
  </div>
</template>

<script>
import Aside from "./Aside.vue";
import Header from "@/components/Header.vue";
import Main from "@/components/Main.vue";

export default {
  name: "Index",
  components:{
    Aside,
    Header,
    Main
  },

};
</script>

<style>

.el-aside {
  color: #333;
}


.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

</style>