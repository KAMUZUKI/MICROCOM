<template>
    <div class="q-px-lg q-pb-md">
      <q-timeline :layout="layout" color="secondary">
        <q-timeline-entry heading>
          Timeline heading
          <br />
        </q-timeline-entry>
        <template v-for="(item, index) in list" :key="index">
          <q-timeline-entry
            :title="item.title"
            :subtitle="item.subtitle"
            :body="item.content"
            :side="(index%2)==0?'right':'left'"
          >
          </q-timeline-entry>
        </template>
      </q-timeline>
    </div>
  </template>
    
<script setup>
  import { useQuasar } from "quasar";
  import { computed } from "vue";
  const $q = useQuasar();
  const list = [
      {
          title: "Event Title",
          subtitle: "February 22, 1986",
          side: "left",
          content: "Lorem ipsum dolor sit amet, consectetur adipisicing elit."
      },
      {
          title: "Event Title",
          subtitle: "February 22, 1986",
          side: "right",
          content: "Lorem ipsum dolor sit amet, consectetur adipisicing elit."
      },
      {
          title: "Event Title",
          subtitle: "February 22, 1986",
          side: "left",
          content: "Lorem ipsum dolor sit amet, consectetur adipisicing elit."
      },
      {
          title: "Event Title",
          subtitle: "February 22, 1986",
          side: "right",
          content: "Lorem ipsum dolor sit amet, consectetur adipisicing elit"
      },
      {
          title: "Event Title",
          subtitle: "February 22, 1986",
          side: "left",
          content: "Lorem ipsum dolor sit amet, consectetur adipisicing elit"
      },
  ];
  const layout = computed(() => {
    return $q.screen.lt.sm ? "dense" : $q.screen.lt.md ? "comfortable" : "loose";
  });
  </script>
    