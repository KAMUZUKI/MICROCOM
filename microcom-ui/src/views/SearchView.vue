<template>
  <div class="bar">
    <input
      id="searchbox"
      v-model="searchBoxRef"
      class="searchbar"
      type="text"
      placeholder="搜索"
    />
    <q-select
      class="select"
      rounded
      standout
      v-model="searchIndex"
      :options="options"
    />
  </div>

  <div class="container">
    <div class="search-panel__filters">
      <h5>Genres</h5>
      <div id="refinement-list"></div>
    </div>
    <div class="search-panel__results">
      <div id="hits"></div>
    </div>
    <div id="pagination"></div>
  </div>
</template>
  
<script setup>
import instantsearch from "instantsearch.js";
import { instantMeiliSearch } from "@meilisearch/instant-meilisearch";
import {
  searchBox,
  hits,
  refinementList,
  pagination,
} from "instantsearch.js/es/widgets";
import { onMounted, ref, watch } from "vue";
import "instantsearch.css/themes/satellite-min.css";

const searchBoxRef = ref(""); // 创建ref引用
const searchIndex = ref("电影");

const options = ["用户", "文章", "电影"];

const initInstantSearch = () => {
  const searchIndexName = ref();

  if (searchIndex.value == "电影") {
    searchIndexName.value = "movies";
  } else if (searchIndex.value == "用户") {
    searchIndexName.value = "users";
  } else {
    searchIndexName.value = "article";
  }

  const search = instantsearch({
    indexName: searchIndexName.value,
    searchClient: instantMeiliSearch(
      "http://localhost:7700",
      "uOCFb1HVp9T5XFw-OnAcQrAWAz6zWsszJdW5G0UVQH4 "
    ),
  });

  if (searchIndex.value == "电影") {
    search.addWidgets([
      searchBox({
        container: "#searchbox",
        placeholder: "Search for products",
      }),
      refinementList({
        container: "#refinement-list",
        attribute: "genres",
        operator: "and",
      }),
      hits({
        container: "#hits",
        templates: {
          item(hit, { html, components }) {
            return html`
              <div class="hit-name">
                <div class="movie_card" id="bright">
                  <div class="info_section">
                    <div class="movie_header">
                      <img class="locandina" src=${hit.poster} />
                      <p class="title" style="font-weight:900;font-size:2rem">
                        ${hit.title}
                      </p>
                      <span class="minutes"
                        >${new Date(hit.release_date).toDateString()}</span
                      >
                      <p class="type" style="font-size:15px;margin-top:20px">
                        ${hit.genres.join("  ")}
                      </p>
                    </div>
                    <div class="movie_desc">
                      <p class="text" style="font-size:18px">
                        ${components.Highlight({ attribute: "overview", hit })}
                      </p>
                    </div>
                  </div>
                  <div
                    class="blur_back"
                    style="background-image: url('${hit.poster}')"
                  ></div>
                </div>
              </div>
            `;
          },
        },
      }),
      pagination({
        container: "#pagination",
      }),
    ]);
  } else {
    search.addWidgets([
      searchBox({
        container: "#searchbox",
        placeholder: "Search for products",
      }),
      refinementList({
        container: "#refinement-list",
        attribute: "categoryid",
        operator: "and",
      }),
      hits({
        container: "#hits",
        templates: {
          item(hit, { html }) {
            return html`
              <div class="hit-name">
                <div class="movie_card" id="bright">
                  <h1>${hit.content}</h1>
                </div>
              </div>
            `;
          },
        },
      }),
      pagination({
        container: "#pagination",
      }),
    ]);
  }

  search.start();

  watch(
    () => searchBoxRef.value,
    (newValue) => {
      // 监听搜索框的值变化
      search.helper.setQuery(newValue).search(); // 调用search方法进行搜索
    }
  );

  search.on("result", (content) => {
    this.hits = content.hits;
  });
};

watch(
  () => searchIndex.value,
  () => {
    // 监听搜索框的值变化
    initInstantSearch();
  }
);

onMounted(() => {
  initInstantSearch();
});
</script>

<style scoped>
.select {
  flex: 0 0 auto;
}

.container {
  margin: 0 auto;
  max-width: 1080px;
}

.bar {
  display: flex;
  margin: 20px auto;
  max-width: 600px;
  border-radius: 30px;
  border: 1px solid #dcdcdc;
  margin-bottom: 20px;
  justify-content: center;
  align-items: center;
}

.bar:hover {
  box-shadow: 1px 1px 8px 1px #dcdcdc;
}

.bar:focus-within {
  box-shadow: 1px 1px 8px 1px #dcdcdc;
  outline: none;
}

.searchbar {
  height: 45px;
  border: none;
  width: 500px;
  font-size: 16px;
  outline: none;
  margin-left: 30px;
}

/* 样式 */
.search-panel__filters {
  float: left;
  width: 200px;
}

.search-panel__results {
  margin-left: 210px;
}
</style>
  
<style lang="scss">
@import url("https://fonts.googleapis.com/css?family=Montserrat:300,400,700,800");

html,
body {
  margin: 0;
  background: rgb(255, 255, 255);
  font-size: 14px;
  font-weight: 400;
}

.movie_card {
  position: relative;
  display: block;
  width: 800px;
  height: 350px;
  overflow: hidden;
  border-radius: 10px;
  transition: all 0.4s;
  text-align: left;

  &:hover {
    transform: scale(1.02);
    transition: all 0.4s;
  }

  .info_section {
    position: relative;
    width: 100%;
    height: 100%;
    background-blend-mode: multiply;
    z-index: 2;
    border-radius: 10px;

    .movie_header {
      position: relative;
      padding: 25px 0 0 25px;
      height: 40%;

      .title {
        color: #fff;
        font-size: 1.5em;
        font-weight: 600;
      }

      .minutes {
        display: inline-block;
        margin-top: 10px;
        color: #fff;
        padding: 5px;
        border-radius: 5px;
        border: 1px solid rgba(255, 255, 255, 0.13);
      }

      .type {
        display: inline-block;
        color: #cee4fd;
        margin-left: 10px;
      }

      .locandina {
        position: relative;
        float: left;
        margin-right: 20px;
        height: 120px;
        box-shadow: 0 0 20px -10px rgba(0, 0, 0, 0.5);
      }
    }

    .movie_desc {
      padding: 25px;
      height: 50%;

      .text {
        color: #cfd6e1;
      }
    }
  }

  .blur_back {
    position: absolute;
    top: 0;
    z-index: 1;
    height: 100%;
    right: 0;
    background-size: cover;
    border-radius: 11px;
  }
}

@media screen and (min-width: 768px) {
  .movie_header {
    width: 80%;
  }

  .movie_desc {
    width: 50%;
  }

  .info_section {
    background: linear-gradient(to right, #7a7a7a 50%, transparent 100%);
  }

  .blur_back {
    width: 80%;
    background-position: -100% 10% !important;
  }
}

@media screen and (max-width: 768px) {
  .movie_card {
    width: 95%;
    margin: 70px auto;
    min-height: 350px;
    height: auto;
  }

  .blur_back {
    width: 100%;
    background-position: 50% 50% !important;
  }

  .movie_header {
    width: 100%;
    margin-top: 85px;
  }

  .movie_desc {
    width: 100%;
  }

  .info_section {
    background: linear-gradient(to top, rgb(20, 20, 19) 50%, transparent 100%);
    display: inline-grid;
  }
}

#bright {
  box-shadow: 0px 0px 150px -45px rgba(0, 0, 0, 0.5);

  &:hover {
    box-shadow: 0px 0px 120px -55px rgba(31, 31, 31, 0.5);
  }
}
</style>