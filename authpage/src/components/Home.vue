<template>
    
    <div>
        <UserDisplay></UserDisplay>
    </div>
</template>

<script>
import UserDisplay from './user/UserDisplay';
export default {
    name : 'Home',
    components:{
        UserDisplay,
    },
    data(){
        return {
            s : 'Hello world',
        }
    },
    mounted(){
        console.log('挂载完成');
    }
}
</script>