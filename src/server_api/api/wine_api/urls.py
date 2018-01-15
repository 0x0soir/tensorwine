from django.urls import path

from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('recognize_photo', views.recognize_photo, name='recognize_photo'),
    path('recommendations', views.recommendations, name='recommendations'),
    path('recommendations/<str:min_rate>/<str:max_rate>', views.recommendations, name='recommendations'),
]
