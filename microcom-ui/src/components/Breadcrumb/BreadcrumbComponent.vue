<template>
    <a-breadcrumb>
        <a-breadcrumb-item>Ant Design Vue</a-breadcrumb-item>
        <a-breadcrumb-item><a href="">Component</a></a-breadcrumb-item>
        <a-breadcrumb-item>
            <a href="">General</a>
            <template #overlay>
                <a-menu>
                    <a-menu-item>
                        <a target="_blank" rel="noopener noreferrer" href="http://www.alipay.com/">General</a>
                    </a-menu-item>
                    <a-menu-item>
                        <a target="_blank" rel="noopener noreferrer" href="http://www.taobao.com/">Layout</a>
                    </a-menu-item>
                    <a-menu-item>
                        <a target="_blank" rel="noopener noreferrer" href="http://www.tmall.com/">Navigation</a>
                    </a-menu-item>
                </a-menu>
            </template>
        </a-breadcrumb-item>
        <a-breadcrumb-item>Button</a-breadcrumb-item>
    </a-breadcrumb>
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
    name: 'BreadcrumbComponent',
    setup() {
        
    },
})
</script>
