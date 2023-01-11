package com.shb.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.*;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author songhaibo
 * @description 统计每人提交Git代码
 * @date 2023-01-10 下午4:36
 */
public class CountLines {
    public static void main(String[] args) throws IOException, GitAPIException {
//        Git git = Git.cloneRepository()
//            .setURI("https://gitlab.mapfarm.com/map_iot/map-iot-middleground.git")
//            .setDirectory(new File("/Users/songhaibo/IdeaProjects/map-iot-middleground"))
//            .setBranch("uat")
//            .setCredentialsProvider(new UsernamePasswordCredentialsProvider("songhaibo", "15232093386bo"))
//            .call();
        //某个人在某月份提交的代码数量
        Git git = Git.open(new File("/Users/songhaibo/IdeaProjects/map-iot-middleground"));
        Repository repository = git.getRepository();
        try (RevWalk revWalk = new RevWalk(repository)) {
            //这里是提交id,通过git log命令可以查看最近一次提交的commitId
            ObjectId commitId = repository.resolve("7ae236fe05b7cb67d12a3cf1302a8bb891f7dd07");///Users/songhaibo/IdeaProjects/map-iot-middleground/
//            ObjectId commitId = repository.resolve("af031d8f420d671ae7073268c0d37ca593c056a1");///Users/songhaibo/IdeaProjects/map-iot-access/
            revWalk.markStart(revWalk.parseCommit(commitId));
            int i = 0;
            Map<String, Object> map = new HashMap<>(16);
            List<Map<String, Object>> mapArrayList = new ArrayList<>();
            for (RevCommit commit : revWalk) {
//                if (commit.getAuthorIdent().getWhen().getTime() < DateUtils.toDate("2022-09-00 00:00:00").getTime()) {
//                    continue;
//                }
                RevCommit parent = null;
                if (commit.getParents().length > 0 && commit.getParent(0) != null) {
                    parent = revWalk.parseCommit(commit.getParent(0).getId());
                }
                if (parent == null) {
                    continue;
                }
                DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
                df.setRepository(repository);
                //设置比较器忽略空白字符
                df.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);
                df.setDetectRenames(true);
                List<DiffEntry> diffs = df.scan(parent.getTree(), commit.getTree());
                int addSizeCount = 0;
                int subSizeCount = 0;
                for (DiffEntry diff : diffs) {
                    FileHeader fileHeader = df.toFileHeader(diff);
                    List<? extends HunkHeader> hunks = fileHeader.getHunks();
                    int addSize = 0;
                    int subSize = 0;
                    for (HunkHeader hunk : hunks) {
                        EditList edits = hunk.toEditList();
                        for (Edit edit : edits) {
                            addSize += edit.getEndB() - edit.getBeginB();
                            subSize += edit.getEndA() - edit.getBeginA();
                        }
                    }
//                        System.out.println("-----changeType=" + diff.getChangeType().name() + "----------新增的行数:" + addSize + "-------删减的行数:" + subSize
//                            + " -----改变的路径:" + diff.getNewPath() + "------改变的文件数:" + diffs.size());
                    addSizeCount = addSize + addSizeCount;
                    subSizeCount = subSize + subSizeCount;
                }
                i++;
//                    System.out.println("一共提交了" + i + "次;" + "------提交注释为:" + commit.getFullMessage() + "-----提交时间为:" + DateUtils.toString(commit.getAuthorIdent().getWhen())
//                        + "----提交人:" + commit.getAuthorIdent().getName());
                //列表map
                Map<String, Object> objectMap = new HashMap<>(16);
                //提交人
                objectMap.put("creteName", commit.getAuthorIdent().getName());
                //提交时间为
                objectMap.put("pushTime", DateUtils.toString(commit.getAuthorIdent().getWhen()));
                //提交时间 格式成月份
                objectMap.put("pushTimeGroup", DateUtils.getyyyy_mm_format().format(commit.getAuthorIdent().getWhen()));
                //提交注释为
                objectMap.put("annotation", commit.getFullMessage());
                //共提交次数
                objectMap.put("commitCount", i);
                //新增的行数
                objectMap.put("added", addSizeCount);
                //删减的行数
                objectMap.put("removed", subSizeCount);
                //剩余的行数
                objectMap.put("total", addSizeCount - subSizeCount);
                mapArrayList.add(objectMap);
            }
            //月汇总map
            Map<Object, Map<Object, List<Map<String, Object>>>> pushTimeGroupMap = mapArrayList.stream()
                .collect(groupingBy(s -> s.get("pushTimeGroup"), groupingBy(s -> s.get("creteName"))));

            Map<Object, Map<Object, Map<String, Object>>> monthCountResult = new HashMap<>();
            pushTimeGroupMap.forEach((k, v) -> {
                Map<Object, Map<String, Object>> nameCountHashMap = new HashMap<>(16);
                v.forEach((k1, v1) -> {
                    int commitCount = v1.stream().mapToInt(s -> (int) s.get("commitCount")).sum();
                    int added = v1.stream().mapToInt(s -> (int) s.get("added")).sum();
                    int removed = v1.stream().mapToInt(s -> (int) s.get("removed")).sum();
                    int total = v1.stream().mapToInt(s -> (int) s.get("total")).sum();
                    Map<String, Object> countHashMap = new HashMap<>(16);
                    countHashMap.put("commitCount", commitCount);
                    countHashMap.put("added", added);
                    countHashMap.put("removed", removed);
                    countHashMap.put("total", total);
//                    objectMap.put("pushTime", v.stream().map(s -> s.get("pushTime")).collect(toList()));
//                    objectMap.put("annotation", v.stream().map(s -> s.get("annotation")).collect(toList()));
                    nameCountHashMap.put(k1, countHashMap);
                });
                monthCountResult.put(k, nameCountHashMap);
            });
            map.put("month", monthCountResult);

            //总汇总map
            Map<Object, List<Map<String, Object>>> creteName = mapArrayList.stream()
                .collect(groupingBy(s -> s.get("creteName")));

            Map<Object, Map<String, Object>> countResult = new HashMap<>();
            creteName.forEach((k, v) -> {
                int commitCount = v.stream().mapToInt(s -> (int) s.get("commitCount")).sum();
                int added = v.stream().mapToInt(s -> (int) s.get("added")).sum();
                int removed = v.stream().mapToInt(s -> (int) s.get("removed")).sum();
                int total = v.stream().mapToInt(s -> (int) s.get("total")).sum();
                Map<String, Object> countHashMap = new HashMap<>(16);
                countHashMap.put("commitCount", commitCount);
                countHashMap.put("added", added);
                countHashMap.put("removed", removed);
                countHashMap.put("total", total);
//                    objectMap.put("pushTime", v.stream().map(s -> s.get("pushTime")).collect(toList()));
//                    objectMap.put("annotation", v.stream().map(s -> s.get("annotation")).collect(toList()));
                countResult.put(k, countHashMap);
            });
            map.put("all", countResult);
            System.out.println(map);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
    }

    public static List<String> getMonthBetweenDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime() <= endDate.getTime()) {
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.MONTH, 1);
                // 获取增加后的日期
                startDate = calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

}
