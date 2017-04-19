/**
 * Created by dylike.
 */
angular.module('meta.umeditor', [])
    .value('metaUmeditorConfig', {
        //杩欓噷鍙互閫夋嫨鑷繁闇�瑕佺殑宸ュ叿鎸夐挳鍚嶇О,姝ゅ浠呴�夋嫨濡備笅涓冧釜
        toolbar: ['source undo redo bold italic underline'],
        //focus鏃惰嚜鍔ㄦ竻绌哄垵濮嬪寲鏃剁殑鍐呭
        autoClearinitialContent: true,
        //鍏抽棴瀛楁暟缁熻
        wordCount: false,
        //鍏抽棴elementPath
        elementPathEnabled: false,
        //frame楂樺害
        //initialFrameHeight: 300
    })
    .directive('metaUmeditor', ['metaUmeditorConfig', function (metaUmeditorConfig) {
        'use strict';

        return {
            restrict: 'AE',
            scope: {
                scopeConfig: '=metaUmeditorConfig'
            },
            require: 'ngModel',
            transclude: true,
            link: function (scope, element, attrs, ngModel) {

                //鑾峰彇鍏ㄥ眬閰嶇疆,涓虹┖
                var config = scope.scopeConfig || metaUmeditorConfig;

                var ctrl = {
                    initialized: false,
                    editorInstance: null,
                    placeholder: attrs['metaUmeditorPlaceholder'] || '',
                    focus: false
                };

                ctrl.init = function () {

                    //鍒涘缓id
                    if (!attrs.id) {
                        attrs.$set('id', 'metaUmeditor-' + Math.floor(Math.random() * 100).toString() + new Date().getTime().toString());
                    }

                    ctrl.createEditor();

                    //閲嶈浇ngModel鐨�$render鏂规硶
                    ngModel.$render = function () {
                        if (ctrl.initialized) {
                            /**
                             * 閲嶈浇ngModel鐨�$render鏂规硶
                             */
                            ctrl.editorInstance.setContent(ngModel.$viewValue || '');
                            ctrl.checkPlaceholder();
                        }
                    };

                    //閲嶈浇ngModel鐨刬sEmpty鏂规硶
                    ngModel.$isEmpty = function (value) {
                        if (!value) {
                            return true;
                        }
                        if (ctrl.initialized) {
                            return !ctrl.editorInstance.hasContents();
                        }
                    };
                };

                //鍒涘缓涓�涓猆MEditor瀹炰緥
                ctrl.createEditor = function () {
                    if (!ctrl.initialized) {
                        ctrl.editorInstance = UM.getEditor(attrs['id'], config);
                        ctrl.editorInstance.ready(function () {
                            ctrl.initialized = true;
                            if(ngModel.$viewValue) {
                              ngModel.$render();
                            } else {
                              ctrl.updateModelView;
                            }
                            ctrl.initListener();
                        });
                    }
                };

                //鐩戝惉澶氫釜浜嬩欢
                ctrl.initListener = function () {
                    ctrl.editorInstance.addListener('contentChange', function () {
                        scope.$evalAsync(ctrl.updateModelView);
                    });
                    ctrl.editorInstance.addListener('focus', function () {
                        ctrl.focus = true;
                        ctrl.checkPlaceholder();
                    });
                    ctrl.editorInstance.addListener('blur', function () {
                        ctrl.focus = false;
                        ctrl.checkPlaceholder();
                    });
                };

                //淇敼ngModel Value
                ctrl.updateModelView = function () {
                    var modelContent = ctrl.editorInstance.getContent();
                    ngModel.$setViewValue(modelContent);
                    if (!scope.$root.$$phase) {
                        scope.$apply();
                    }
                };

                //鐩戞祴鏄惁闇�瑕乸laceholder
                ctrl.checkPlaceholder = function () {
                    var parent =
                        angular.element('#' + attrs['id']).parent();
                    if (ctrl.focus || ctrl.editorInstance.hasContents()) {
                        parent.children('.metaUmeditorPlaceholder').remove();
                    } else {
                        parent.css('position', 'relative').append('<div class="metaUmeditorPlaceholder" style="position:absolute;top:0;left:0;padding:0 10px;line-height: 24px;color:#ccc">' + ctrl.placeholder + '</div>')
                    }
                };

                ctrl.init();
            }
        }
    }]);

