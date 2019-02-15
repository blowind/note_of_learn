
/*webpack 2.0以上的版本可以直接引入json文件，不需要借助json-loader*/
import HomePageData from "./data.json"

var slider_images = require.context('./sliders', false, /\.(png|jpg|gif|svg)$/)
var cover_images = require.context('./covers', false, /\.(png|jpg|gif|svg)$/)


HomePageData.top.forEach((x)=> {
    x.img_url = slider_images('./' + x.img_url)
})

HomePageData.promotions.forEach((x)=> {
    x.img_url = cover_images('./' + x.img_url)
})

HomePageData.recommended.forEach((x)=> {
    x.img_url = cover_images('./' + x.img_url)
})

export default {
    getHomeData() {
        return HomePageData
    }
}