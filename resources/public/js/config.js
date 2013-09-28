require.config({
    baseUrl: "js",
    paths: {
        "jquery": "vendor/jquery",
        "underscore": "vendor/underscore",
        "backbone": "vendor/backbone"
    },
    shim: {
        "jquery": {
           "exports": "$",
        },
        "underscore": {
          "exports": "_"
        },
        "backbone": {
          "deps": ["jquery", "underscore"],
          "exports": "Backbone"
        }
      }
});
