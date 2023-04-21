<script>
import { notification } from 'ant-design-vue';
import { defineComponent} from 'vue';
export default defineComponent({
    name: 'NotificationComponent',
    setup() {
        const openNotificationWithIcon = (type, status, message) => {
            notification[type]({
                message: status,
                description: message,
                duration: 2,
            });
        };
        return {
            openNotificationWithIcon,
        };
    },
});
</script>