// vue.config.js
module.exports = {
    // https://cli.vuejs.org/config/#devserver-proxy
    runtimeCompiler: true,
    devServer: {
        port: 3000,
        proxy: {
            '/api': {
                target: 'http://localhost:8083',
                ws: true,
                changeOrigin: true
            }
        }
    }
}