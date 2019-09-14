package com.example.assemblywebmagic.demo;

import org.apache.commons.lang3.math.NumberUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatisticsPageProcessor implements PageProcessor {

    static String driver = "com.mysql.cj.jdbc.Driver";
    static String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static String username = "root";
    static String password = "123456";
    static Connection conn = null;

    static{
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn =DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setRetryTimes(3).setSleepTime(1500);
    }

    @Override
    public void process(Page page) {
        if(page.getUrl().get().contains("index.html")){//省份
            List<TargetModel> provinceModels = new ArrayList<>();
            List<String> provices = page.getHtml().xpath("tr[@class='provincetr']/td/a/text()").all();
            List<String> urls = page.getHtml().xpath("tr[@class='provincetr']/td/a").links().all();
            TargetModel model = null;
            for(int i = 0 ; i< provices.size() && (provices.size() == urls.size()); i ++){
                model = new TargetModel();
                String url = urls.get(i);
                Pattern p1 = Pattern.compile("\\d+\\.html$");
                Matcher m1 = p1.matcher(url);
                if(m1.find()){
                    String group1 = m1.group();
                    Pattern p2 = Pattern.compile("\\d+");
                    Matcher m2 = p2.matcher(group1);
                    if(m2.find()){
                        String group2 = m2.group();
                        if(NumberUtils.isCreatable(group2)){
                            model.setId(Integer.valueOf(group2));
                        }
                    }
                }
                model.setPId(0);
                model.setName(provices.get(i));
                model.setUrls(url);
                model.setLevel("1");
                model.setSort(model.getId().toString());
                model.setCode(model.getId().toString());
                model.setLongcode(model.getId().toString());
                provinceModels.add(model);
                page.addTargetRequest(url);
            }
            page.putField("provinces", provinceModels);
        }else if(page.getUrl().regex("http://www\\.stats\\.gov\\.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/[0-9]*.html").match()){//市级
            List<TargetModel> cityModels = new ArrayList<>();
            List<String> cities = page.getHtml().xpath("tr[@class='citytr']/td[2]/a/text()").all();
            List<String> urls = page.getHtml().xpath("tr[@class='citytr']/td[2]/a").links().all();
            Integer tmId = null;
            //解析父省份id设置到当前对象
            String url = page.getUrl().get();
            Pattern p1 = Pattern.compile("\\d+\\.html$");
            Matcher m1 = p1.matcher(url);
            if(m1.find()){
                String group1 = m1.group();
                Pattern p2 = Pattern.compile("\\d+");
                Matcher m2 = p2.matcher(group1);
                if(m2.find()){
                    String group2 = m2.group();
                    if(NumberUtils.isCreatable(group2)){
                        tmId = Integer.valueOf(group2);
                    }
                }
            }
            for(int i = 0; (i < cities.size() && (cities.size() == urls.size())); i ++){
                TargetModel tm = new TargetModel();
                String cUrl = urls.get(i);
                Pattern p3 = Pattern.compile("\\d+\\.html$");
                Matcher m3 = p3.matcher(cUrl);
                if(m3.find()){
                    String group3 = m3.group();
                    Pattern p4 = Pattern.compile("\\d+");
                    Matcher m4 = p4.matcher(group3);
                    if(m4.find()){
                        String group4 = m4.group();
                        if(NumberUtils.isCreatable(group4)){
                            tm.setId(Integer.valueOf(group4));
                        }
                    }
                }
                tm.setPId(tmId);
                tm.setName(cities.get(i));
                tm.setUrls(cUrl);
                tm.setLevel("2");
                tm.setSort(tm.getId().toString());

                tm.setCode(tmId.toString());
                String ddd = "000000000000";
                tm.setLongcode(tmId.toString()+ddd.substring(0,12-tmId.toString().length()));

                cityModels.add(tm);
                page.addTargetRequest(cUrl);
            }//table[@class='villagetable']/tbody/tr[@class='villagetr']/td[1]
            page.putField("cities", cityModels);
        }else if(page.getUrl().regex("http://www\\.stats\\.gov\\.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/[0-9]*/[0-9]*.html").match()){//区域
            List<TargetModel> areaModels = new ArrayList<>();
            List<String> cities = page.getHtml().xpath("tr[@class='countytr']/td[2]/a/text()").all();
            List<String> urls = page.getHtml().xpath("tr[@class='countytr']/td[2]/a").links().all();

//            List<String> code = page.getHtml().xpath("//table[@class='villagetable']/tbody/tr[@class='villagetr']/td[1]/text()").all();

            //解析父区域id设置到当前对象
            Integer tmId = null;
            String url = page.getUrl().get();
            Pattern p1 = Pattern.compile("\\d+\\.html$");
            Matcher m1 = p1.matcher(url);
            if(m1.find()){
                String group1 = m1.group();
                Pattern p2 = Pattern.compile("\\d+");
                Matcher m2 = p2.matcher(group1);
                if(m2.find()){
                    String group2 = m2.group();
                    if(NumberUtils.isCreatable(group2)){
                        tmId = Integer.valueOf(group2);
                    }
                }
            }
            for(int i = 0; (i < cities.size() && (cities.size() == urls.size())); i ++){
                TargetModel tm = new TargetModel();
                String cUrl = urls.get(i);
                Pattern p3 = Pattern.compile("\\d+\\.html$");
                Matcher m3 = p3.matcher(cUrl);
                if(m3.find()){
                    String group3 = m3.group();
                    Pattern p4 = Pattern.compile("\\d+");
                    Matcher m4 = p4.matcher(group3);
                    if(m4.find()){
                        String group4 = m4.group();
                        if(NumberUtils.isCreatable(group4)){
                            tm.setId(Integer.valueOf(group4));
                        }
                    }
                }
                tm.setPId(tmId);
                tm.setName(cities.get(i));
                tm.setUrls(cUrl);
                tm.setLevel("3");
                tm.setSort(tm.getId().toString());

                tm.setCode(tmId.toString());
                String ddd = "000000000000";
                tm.setLongcode(tmId.toString()+ddd.substring(0,12-tmId.toString().length()));

                areaModels.add(tm);
            }
            page.putField("areas", areaModels);
        }
    }

    public static void main(String[] args) {
        CustomPipeline customPipeline = new CustomPipeline();
        Spider spider = Spider.create(new StatisticsPageProcessor());
        spider.addUrl("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/index.html");
        spider.addPipeline(customPipeline);
        spider.start();
        for(;;){
            if(spider.getStatus().equals(Spider.Status.Stopped)){
                break;
            }
        }
        List<TargetModel> provinces = customPipeline.provinces;
        //当所有任务执行完毕
        //INSERT INTO `wool_trade`.`bus_region` (`id`, `name`, `p_id`) VALUES ('1', '北京市', '0');

        String sql = "INSERT INTO `test`.`region` (`id`, `pid`, `name`, `level`, `sort`, `code`, `longcode`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement;
        try {
            for(TargetModel province : provinces){
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, province.getId());
                preparedStatement.setInt(2, province.getPId());
                preparedStatement.setString(3, province.getName());
                preparedStatement.setString(4, province.getLevel());
                preparedStatement.setString(5, province.getSort());
                preparedStatement.setString(6, province.getCode());
                preparedStatement.setString(7, province.getLongcode());
                preparedStatement.executeUpdate();

                //遍历市级
                if(province.getChilds()==null){continue;}
                for(TargetModel city : province.getChilds()){
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setInt(1, city.getId());
                    preparedStatement.setInt(2, city.getPId());
                    preparedStatement.setString(3, city.getName());
                    preparedStatement.setString(4, city.getLevel());
                    preparedStatement.setString(5, city.getSort());
                    preparedStatement.setString(6, city.getCode());
                    preparedStatement.setString(7, city.getLongcode());
                    preparedStatement.executeUpdate();

                    //遍历区域
                    if(city.getChilds()==null){continue;}
                    for(TargetModel area : city.getChilds()){
                        preparedStatement.setInt(1, area.getId());
                        preparedStatement.setInt(2, area.getPId());
                        preparedStatement.setString(3, area.getName());
                        preparedStatement.setString(4, area.getLevel());
                        preparedStatement.setString(5, area.getSort());
                        preparedStatement.setString(6, area.getCode());
                        preparedStatement.setString(7, area.getLongcode());
                        preparedStatement.executeUpdate();
                    }
                }
            }
        }catch (SQLException e) {
        }
    }
}
