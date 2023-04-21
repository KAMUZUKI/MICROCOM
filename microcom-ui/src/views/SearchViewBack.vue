<template>
    <div class="container">
        <input v-model="deleteName" />
        <q-btn class="q-mb-md" color="primary" @click="deleteByIndex()">删除</q-btn>
        <q-tabs class="text-dark shadow-2 navbar" indicator-color="purple" align="center">
          <q-route-tab active-class="active_tab" :to="{ name: 'user' }" label="用户" exact />
          <q-route-tab :to="{ name: 'movie' }" label="电影" exact />
          <q-route-tab :to="{ name: 'article' }" label="文章" exact />
        </q-tabs>
        <ais-instant-search :search-client="searchClient" index-name="movies">
            <div class="search-panel__filters">
                <!-- 其他组件 -->
                <h5>Genres</h5>
            </div>
            <div class="search-panel__results">
                <ais-search-box placeholder="Search here…" :show-loading-indicator="true"/>
                <ais-hits>
                    <template #default="{ items }">
                        <div v-for="item in items" :key="item.objectID">
                          <div class="movie_card" id="bright">
                                <div class="info_section">
                                    <div class="movie_header">
                                        <img class="locandina"
                                            :src=item.poster />
                                        <text class="title">{{ item.title }}</text>
                                        <div>
                                            <template v-for="tag,index in item.genres" :key="index">
                                              <p class="type">{{ tag }}</p>
                                            </template>
                                        </div>
                                        <p class="minutes">{{ (new Date(item.release_date)).toDateString() }}</p>
                                    </div>
                                    <div class="movie_desc">
                                        <p class="text">
                                          <ais-snippet :hit="item" attribute="overview" />
                                        </p>
                                    </div>
                                </div>
                                <div class="blur_back" :style="{'background-image': `url(${item.poster})`}"></div>
                            </div>
                        </div>
                    </template>
                </ais-hits>
                <ais-configure 
                  :attributesToSnippet="['overview:50']" 
                  snippetEllipsisText="…">
                  <template v-slot="{ searchParameters, refine }">
                    <button
                      @click="refine({
                        ...searchParameters,
                        enableRules: !searchParameters.enableRules,
                      })"
                    >Toggle only query rules</button>
                    Currently applied filters:
                    <pre>{{ searchParameters }}</pre>
                  </template>
                </ais-configure>
                <!-- :hits-per-page.camel="10" 
                :distinct="true" 
                :enable-personalization.camel="true"
                :facet-filters="['isDel:false']"  -->
            </div>
        </ais-instant-search>
    </div>
</template>
  
<script setup>
import "instantsearch.css/themes/algolia-min.css";
import { instantMeiliSearch } from "@meilisearch/instant-meilisearch";
import { MeiliSearch } from "@meilisearch/instant-meilisearch/node_modules/meilisearch";
import { ref } from "vue";
import {
    AisInstantSearch,
    AisSearchBox,
    AisConfigure,
    AisHits,
    AisSnippet,
} from "vue-instantsearch/vue3/es";

const searchClient = instantMeiliSearch(
    "http://localhost:7700",
    "GwxvoQQt_vk0iQwiBf82WnyrfdYW7kv7fRKMYJnkU84",
    {
        placeholderSearch: true,
        finitePagination: true,
        matchingStrategy: 'all',
        keepZeroFacets: true,
    }
);

const client = new MeiliSearch({
    host: "http://localhost:7700",
    apiKey: "GwxvoQQt_vk0iQwiBf82WnyrfdYW7kv7fRKMYJnkU84",
});

const deleteName = ref();

const deleteByIndex = () => {
    const index = client.index(deleteName.value);
    index.delete();
    alert(deleteName.value + "删除成功");
};

</script>
  
<style scoped>
.container {
    max-width: 1400px;
    margin: 0 auto;
    padding: 0 20px;
}
</style>

<style>
.active_tab{
  background: #fff;
  color: #000;
}

.ais-SearchBox {
    /* 修改样式 */
    border: none;
    max-width: 50%;
    margin: 0 auto;
    border-radius: 20px;
    /* ... */
}

.ais-Hits-item {
    margin-bottom: 1em;
    width: calc(50% - 1rem);
}

.ais-Hits-item img {
    margin-right: 1em;
    width: 100%;
    height: 100%;
    margin-bottom: 0.5em;
}

.ais-Highlight-highlighted {
    background: cyan;
    font-style: normal;
}

.disclaimer {
    margin-left: 1em;
}

.hit-name {
    margin-bottom: 0.5em;
}

.hit-info {
    font-size: 90%;
}

.container {
    padding: 1rem;
}

.hit-description {
    font-size: 90%;
    margin-bottom: 0.5em;
    color: grey;
}

.ais-SearchBox-input {
    border-radius: 20px;
}

.search-panel__filters {
    float: left;
    width: 200px;
}

.search-panel__results {
    margin-left: 210px;
}

.ais-Pagination {
    margin: 2rem auto;
    text-align: center;
}
</style>
  
<style lang="scss" scoped>

html, body{
  margin: 0;
  background: rgb(255, 255, 255);
  font-size: 14px;
  font-weight: 400;
}

.movie_card{
  position: relative;
  display: block;
  width: 800px;
  height: 350px;
  margin: 100px auto; 
  overflow: hidden;
  border-radius: 10px;
  transition: all 0.4s;
  &:hover{
    transform: scale(1.02);
    transition: all 0.4s;
  }
  .info_section{
    position: relative;
    width: 100%;
    height: 100%;
	background-blend-mode: multiply;
    z-index: 2;
    border-radius: 10px;
    .movie_header{
      position: relative;
      padding: 25px;
      height: 40%;
      .title{
        color: #fff;
        font-size: 1.5em;
        font-weight: 600;
      }
      .minutes{
        display: inline-block;
        margin-top: 10px;
        color: #fff;
        padding: 5px;
        border-radius: 5px;
        border: 1px solid rgba(255,255,255,0.13);
      }
      .type{
        display: inline-block;
        color: #cee4fd;
        margin-left: 10px;
      }
      .locandina{
        position: relative;
        float: left;
        margin-right: 20px;
        height: 120px;
        box-shadow: 0 0 20px -10px rgba(0,0,0,0.5);
      }
    }
    .movie_desc{
      padding: 25px;
      height: 50%;
      .text{
        color: #cfd6e1;
      }
    }
  }
  .blur_back{
    position: absolute;
    top: 0;
    z-index: 1;
    height: 100%; right: 0;
    background-size: cover;
    border-radius: 11px;
  }
}

@media screen and (min-width: 768px) {
  .movie_header{
    width: 60%;
  }
  
  .movie_desc{
    width: 50%;
  }
  
  .info_section{
    background: linear-gradient(to right, #7a7a7a 50%, transparent 100%);
  }
  
  .blur_back{
    width: 80%;
    background-position: -100% 10% !important;  
  }
}

@media screen and (max-width: 768px) {
  .movie_card{
    width: 95%;
    margin: 70px auto; 
    min-height: 350px;
    height: auto;
  }
  
  .blur_back{
    width: 100%;
    background-position: 50% 50% !important;  
  }
  
  .movie_header{
    width: 100%;
    margin-top: 85px;
  }
  
  .movie_desc{
    width: 100%;
  }
  
  .info_section{
    background: linear-gradient(to top, rgb(20, 20, 19) 50%, transparent 100%);
    display: inline-grid;
  }
}


#bright{
  box-shadow: 0px 0px 150px -45px rgba(255, 51, 0, 0.5);
  &:hover{
    box-shadow: 0px 0px 120px -55px rgba(255, 51, 0, 0.5);
  }
}
</style>