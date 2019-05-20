package com.limynl.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.limynl.project.R;
import com.limynl.project.adapter.ContentNewsAdapter;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.entity.TestContentNewsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsActivity extends TopBarBaseActivity  {
    @BindView(R.id.news_list)
    ListView newsList;
    private List<TestContentNewsBean> allDataList = new ArrayList<>();
    private ContentNewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_news;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("资讯专栏");
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        initData();
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("content", allDataList.get(i).getContent());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void initData(){
        String result = "{\n" +
                "  \"status\": 1,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": 10,\n" +
                "      \"name\": \"血小板升了又降，又加上紫癜反复怎么办？\",\n" +
                "      \"picSmall\": \"https://www.zyzpes.com/img.php?url=http://inews.gtimg.com/newsapp_match/0/7819772522/0\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/550b86560001009406000338.jpg\",\n" +
                "      \"description\": \"血小板升了又降，又加上紫癜反复怎么办？\",\n" +
                "      \"content\": \"血小板升而复降，不升反降，皮肤紫癜反复出现，这些都是血小板减少患者日常生活中需要面对的主要问题，那么，患者该怎样应对呢?\n" +
                "\n" +
                "血小板减少性紫癜简称ITP。轻型或急性期病例预后良好，甚至能够自愈。超过半年转成慢性就比较难治。用激素或丙种球蛋白血小板虽能很快升高，但不持久，而且价格贵还有副作用。\n" +
                "\n" +
                "而传统中医中药能减少出血，但药方多为凉血止血，清热解毒，补血益气的功效，不能才能够根本上就诊病因，使全身状况好转，升高血小板不理想。\n" +
                "\n" +
                "因此，目前患者们多采取中西医结合的治疗方式，中西医优势互补，标本兼治，对血小板数值的提升、止血以及免疫机能的稳定都有着非常好的效果。\n \",\n" +
                "      \"learner\": 84545\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 11,\n" +
                "      \"name\": \"2019年血液病友过年注意事项阅读并理解！\",\n" +
                "      \"picSmall\": \"http://inews.gtimg.com/newsapp_ls/0/6336161383_240180/0\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/551916790001125706000338.jpg\",\n" +
                "      \"description\": \"2019年血液病友过年注意事项阅读并理解！\",\n" +
                "      \"content\": \"一、身体素质不好，血象异常的血液病患者，特别是有感染出血风险，贫血严重的患病群体。需要注意选择交通工具，特别是飞机的乘坐，在远方回家的血液病患者，需要等血象在安全水平了才可选择交通工作进行长途回家。有感染风险的血液病患者，需要远离拥挤的车厢，避免引起感染（关注白系/中性粒细胞）。\n" +
                "\n" +
                "\n" +
                "二、饮食的注意，这一点很重要。无论是你已经减量停药，或是在服药状态，亦或是已经达到临床康复。都需要保持合理的饮食习惯，过年的时候大鱼大肉比较多，要注意饮食上的荤素搭配，千万不要暴饮暴食。且注意消食锻炼，这里推荐正在进行化疗药物治疗的血液病患者，荤素食物的搭配可以3:7这样去进行。还有一点就是食品的安全，需要干净，不要口不择食，可以多选择植物性蛋白，尽量避免太油腻，油炸，辛辣的食物，身子弱的话过夜的食物也尽量不要吃。\n" +
                "\n" +
                "三、过年所在场所的注意，人多的地方就拥挤，且近段时间冷空气流感的袭击都可能造成血液病患者发生并发症情况，注意保暖。还有大面积放鞭炮的地方，尽量远离，身体素质差的血液病患者，要远离人多空气质量差的地方。包括过年期间的互串亲戚，旅游等。\n" +
                "\n" +
                "四、环境的卫生跟食物的卫生都是一样重要的，血液病患者所待的家中，房间要注意勤加打扫，保持环境的清洁性，开窗透气！还在高强度治疗期间或是移植后恢复阶段的血液病患者所居住的环境有必要进行次数时间间隔的消毒措施。\n" +
                "\n" +
                "\n" +
                "五、保持心情的愉悦性，不要大发脾气，以免发生病情加重的情况。曾经就有一位血小板数值暂时安全的血液病患者回家过年，因为跟家人吵架血小板一下子掉到个位数，新年的时候只能在医院的ICU中度过......可见保持一个好的心情是很重要的，如果心理压力大无法排解有必要救助心理医师。\n" +
                "\n" +
                "六、了解一些急救措施信号，如有发生高热，胸闷，疼痛，憋气，发胀或是头部剧烈疼痛、身体部位非创伤性自发出血（注意出血量），晕厥等情况，需要及时就医检测治疗，以防万一！\n \",\n" +
                "      \"learner\": 18411\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 12,\n" +
                "      \"name\": \"小小的伤口长时间不痊愈，应及时就医防止血液疾病！\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/5518ecf20001cb4e06000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/5518ecf20001cb4e06000338.jpg\",\n" +
                "      \"description\": \"小小的伤口长时间不痊愈，应及时就医防止血液疾病！\",\n" +
                "      \"content\": \"血小板减少会带来流血止不住，常见的是皮下出血，也就是我们在血液病科中较为常见的血小板减少性紫癜。血小板在体内正常的数量为10万~30万，低于10万，对于有的人来说无碍。低于5万以下的就属于有生命危险了，你运动量大的话，能引起脑出血，甚至内脏出血，从而危及生命。\n" +
                "\n" +
                "\n" +
                "日常生活中，偶尔磕磕碰碰在所难免，譬如切菜、削水果可能会留下刀伤，穿上较硬的鞋子可能会磨伤等。碰到这些小伤口，绝大多数人都会自行处理。在受伤之后，贴张创可贴是很多人对付小伤口的首选。但创可贴自身也有不少弊端，使用创可贴可以起到暂时止血、保护创面的作用。但应该注意的是，使用创可贴的时间不宜过长，最好不要超过两天。如果使用时间过长，由于创可贴外层的胶布不透空气，会使伤口和伤口周围的皮肤发白、变软。另外，常用的创可贴本身达不到无菌标准，也不能起到灭菌的作用，很容易导致继发！\n" +
                "\n" +
                "\n" +
                "对患有糖尿病、脉管炎、下肢静脉曲张等疾病的人来说，小伤口如果没有处理好，可能长时间无法愈合，从而引发感染、败血症、截肢等严重后果，甚至死亡。而我们常用的创可贴并不适合上述患者，正确的做法是用碘伏对伤口进行消毒处理后，用一层或两层透气性好的纱布对伤口局部包扎，并注意及时更换纱布和局部消毒。\n" +
                "\n" +
                "\n" +
                "血小板在止血、伤口愈合、炎症反应、血栓形成及器官移植排斥等生理和病理过程中有重要作用,血小板对毛细血管壁有营养和支持作用,血小板数量减少时,毛细血管易破裂,皮肤、粘膜就会出现出血点.正常妇女血小板为(100-300)X109/L,妊娠后血小板数目、外形、功能均无明显改变,孕妇如发现自己身上有皮下出血点或粘膜出血,不可大意应及时到医院治疗。\n \",\n" +
                "      \"learner\": 56432\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 13,\n" +
                "      \"name\": \"ITP免疫紊乱才是“本”，如何标本兼治？\",\n" +
                "      \"picSmall\": \"https://www.zyzpes.com/img.php?url=http://inews.gtimg.com/newsapp_match/0/7151524909/0\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/5518bbe30001c32006000338.jpg\",\n" +
                "      \"description\": \"ITP免疫紊乱才是“本”，如何标本兼治？\",\n" +
                "      \"content\": \"免疫性血小板减少症患者多有病情反复发作，迁延不愈数年的现象，为何血小板减少治不好呢?\n" +
                "\n" +
                "\n" +
                "其实，所谓免疫性血小板减少症(ITP)是因为患者自身免疫机能失衡紊乱所致，其他如感冒，病毒感染，药物，疾病等都为诱发因素，也就是说，免疫机能紊乱才是“本”，血小板减少只是“表象”，通过治疗，仅仅是提升血小板数值是治不好这个病的!\n" +
                "\n" +
                "一部分患者，血清抗血小板抗体(PAIgG)或抗核抗体(ANA)及其它自身抗体，多为阳性结果，且多呈现高滴度。从而支持患者血小板减少，是由于大量的自身抗体，与自身血小板上的抗原位点结合，在补体的参与下，激活自体免疫反应，破坏血小板膜结构，导致血小板破碎，或在脾脏及其他网状内皮系统被清除。\n" +
                "\n" +
                "\n" +
                "同时，多数免疫性血小板减少患者都是因为巨核细胞产板不足导致的血小板计数减少现象，甚至发生能够产血小板的巨核细胞缺如现象，从而认为，自身免疫紊乱，不仅表现在自身抗体破坏循环血液中的自体血小板，同时也影响到巨核细胞生产血小板的环节。\n" +
                "\n" +
                "也就是说，血小板被破坏，必须经过抗原抗体结合、补体参与、免疫损伤等系列反应。\n" +
                "\n" +
                "因此，在针对免疫性血小板减少症的治疗过程中，首先就要从调节患者机体免疫机能着手，平衡免疫机制，清除抗血小板抗体，同时，改善巨核细胞产板功能，以便能够从根源上提升血小板数值，达到标本兼治的目的。\n \",\n" +
                "      \"learner\": 25210\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 14,\n" +
                "      \"name\": \"激素吃了这么久，血小板减少该怎么减药？\",\n" +
                "      \"picSmall\": \"https://www.zyzpes.com/img.php?url=http://inews.gtimg.com/newsapp_match/0/5427775179/0\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/551380400001da9b06000338.jpg\",\n" +
                "      \"description\": \"激素吃了这么久，血小板减少该怎么减药？\",\n" +
                "      \"content\": \"如上述免疫指标(淋巴细胞亚群，CD20，T淋巴细胞活化状态以及调节T细胞等)转为正常并且血小板相关抗体已经转为阴性，则继续每3周减量直至停药;\n" +
                "\n" +
                "如上述指标仍有异常(最常见的是仍有B细胞亚群的比例升高或者调节T细胞比例的持续低下)，则暂停减量，并以目前有效的小剂量激素口服持续维持12周(3个月)至24周(6个月)，再次复查上述指标后再决定下一步的治疗;\n" +
                "\n" +
                "必要时需联合用其他免疫抑制药物，如依木兰(硫唑嘌呤)，环孢素A(CsA)等。\n" +
                "\n" +
                "\n" +
                "4、激素尽量于早上一次性顿服，服用激素期间应避免使用免疫增强剂(如干扰素、匹多莫得、复可托及必思添等)，同时避免进行免疫预防接种。\n" +
                "\n" +
                "5、如需防控因使用激素导致的呼吸道感染，可以使用静脉用免疫球蛋白(IVIG，丙种球蛋白)按200-300mg/kg静脉输注，每月1次。\n" +
                "\n" +
                "6、激素停药后1-2年内，避免使用免疫增强剂(如干扰素、匹多莫得、复可托及必思添等)，同时避免进行免疫预防接种。\n" +
                "\n" +
                "由此可见，激素类药物治疗也并非万无一失的，而且，在激素药物治疗过程中，大家也可以考虑结合中医药辩证论治，让患者的病情尽早恢复，患者也可以达到正常的生活状态。\n \",\n" +
                "      \"learner\": 56445\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 15,\n" +
                "      \"name\": \"为什么输血或输板，都不能有效长久提升血小板数值\",\n" +
                "      \"picSmall\": \"https://www.zyzpes.com/img.php?url=http://inews.gtimg.com/newsapp_match/0/3647618204/0\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/550a33b00001738a06000338.jpg\",\n" +
                "      \"description\": \"为什么输血或输板，都不能有效长久提升血小板数值\",\n" +
                "      \"content\": \"输血小板可以很快把血小板数值提升上去，可为什么实际治疗时，都不让经常输板维持血小板数值呢?血小板太低，不是更危险吗?\n" +
                "\n" +
                "\n" +
                "医生：\n" +
                "\n" +
                "输血输板作为支持治疗的一种主要手段，仅仅作为严重出血时的紧急治疗措施，不能作为常规治疗手段。\n" +
                "\n" +
                "尤其是部分血小板减少患者体内存在抗血小板抗体，输入的血或血小板很快被破坏，寿命短暂，可能只有几分钟到几个小时。\n" +
                "\n" +
                "因此，输血或输板，都不能长期有效提升血小板数值，甚至还可能产生更严重的不良反应，“雪上加霜”!\n \",\n" +
                "      \"learner\": 45658\n" +
                "    },\n"  +
                "    {\n" +
                "      \"id\": 17,\n" +
                "      \"name\": \"年纪大了血小板低下，你还需要关注这些问题\",\n" +
                "      \"picSmall\": \"https://www.zyzpes.com/img.php?url=http://inews.gtimg.com/newsapp_match/0/6766521292/0\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/5513e20600017c1806000338.jpg\",\n" +
                "      \"description\": \"年纪大了血小板低下，你还需要关注这些问题\",\n" +
                "      \"content\": \"虽然血小板减少症多发于儿童和年轻女性，但老年人血小板减少患者也并不少见。为何老年人会得血小板减少呢?\n" +
                "\n" +
                "\n" +
                "其实，血小板减少症的发生于年龄段，性别等并没有绝对直接的关联，任何人都会生病，而血小板减少的发生最根本的原因还是因为人体免疫机能失衡紊乱所致，受各种外界因素的影响诱发患病。\n" +
                "\n" +
                "而老年人之所以会患上血小板减少这种疾病，一是因为老年人机体各脏腑机能都处于衰退时期，机体免疫机能也是一样的，对疾病的抵抗力降低;二是外界诱发因素众多，常见如炎症和感冒病毒感染，化学毒物，电离辐射，放射性因素，过度劳累，睡眠不好等，还有一些患者伴有高血压，糖尿病等老年性疾病，药物也可诱发血小板减少的发生。\n" +
                "\n" +
                "\n" +
                "老年人得了血小板减少怎么办?\n" +
                "\n" +
                "血小板减少的主要危害就是出血，尤其是老年人，一旦血小板数值过低，出血的风险增加，随时都可能面临出血导致的更严重的危害，因此，检查发现老人血小板数值低，要积极治疗。\n" +
                "\n" +
                "不过，需要提醒大家的是，老年人这个群体除了血小板减少外，还可能同时伴有其他疾病，如高血压，糖尿病等，要注意不同疾病的药物对血小板数值的影响，尤其是激素类药物，很容易加重高血压，糖尿病病情，而治疗高血压，糖尿病的药物又会反作用于血小板，导致其数值反复下降。\n" +
                "\n" +
                "因此，老年血小板减少最好结合具体病情选择更合理有效的中西医结合治疗方案，避免因西医药物使用不当引起患者病情反复，迁延不愈。\n \",\n" +
                "      \"learner\": 46321\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 18,\n" +
                "      \"name\": \"病情长期不愈，很容易发展为慢性血小板减少症\",\n" +
                "      \"picSmall\": \"https://www.zyzpes.com/img.php?url=http://inews.gtimg.com/newsapp_match/0/4448557478/0\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/550a78720001f37a06000338.jpg\",\n" +
                "      \"description\": \"病情长期不愈，很容易发展为慢性血小板减少症\",\n" +
                "      \"content\": \"病属咨询：孩子自从去年发现血小板减少，一直口服激素治疗，但效果总是不太好，时升时降，反反复复的快一年了，中间停药一段时间，一次感冒就被“打回原形”了!作为家长很着急，孩子还这么小，什么时候才能治好?\n" +
                "\n" +
                "\n" +
                "血液科医生：\n" +
                "\n" +
                "特发性血小板减少性紫癜现在一般称为免疫性血小板减少性紫癜(ITP)，是小儿最常见的出血性疾病。对于无出血或轻微出血的ITP，可以不考虑血小板计数，严密观察即可，不一定需要药物或其他治疗;对于有鼻出血、黏膜出血者可考虑干预。\n" +
                "\n" +
                "激素和丙球治疗一般是有效的，部分患儿血小板计数容易反复下降，所以对于需要应用激素的患儿，一定要注意监测孩子血常规变化，规律调整，适时减量，同时注意避免上呼吸道感染等，可尽早配合其他治疗方式逐渐减停激素。\n" +
                "\n" +
                "\n" +
                "因为在针对孩子血小板减少情况的治疗过程中，长期用激素药物的副作用也是不可忽视的，尤其是孩子正处于生长发育时期，激素药物容易对患者的内分泌系统产生影响，发生增重，满月脸，痤疮，多毛，骨质疏松等副作用，因此，家长还要对孩子的健康状况及时关注。\n" +
                "\n" +
                "孩子病情长期不愈，很容易发展为慢性血小板减少症，尤其是病程超过半年以上的患者，对此，不建议长期依靠激素类药物维持血小板数值，可以联合中医辩证用药，中西医结合治疗在稳定提升血小板数值的同时，减少副作用对孩子的影响!\n \",\n" +
                "      \"learner\": 12130\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 19,\n" +
                "      \"name\": \"发病率高的人群：血小板减少性紫癜也“挑人”\",\n" +
                "      \"picSmall\": \"https://www.zyzpes.com/img.php?url=http://inews.gtimg.com/newsapp_match/0/6492744435/0\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/550a836c0001236606000338.jpg\",\n" +
                "      \"description\": \"发病率高的人群：血小板减少性紫癜也“挑人”\",\n" +
                "      \"content\": \"血小板减少性紫癜是血液科临床上最常见的出血性疾病之一，因血小板数值持续偏低导致的自发性出血现象，这也让患者们随着对疾病认知的加深而逐渐感到恐惧。\n" +
                "\n" +
                "\n" +
                "其实，血小板减少性紫癜是否发生也是“看人”的，并非所有人都会成为血小板减少的攻击对象。\n" +
                "\n" +
                "目前，常见的血小板减少性紫癜的“高危”人群包括：\n" +
                "\n" +
                "儿童：\n" +
                "\n" +
                "儿童血小板减少性紫癜比较常见，但血小板减少症有急慢性之分，而儿童血小板减少患者多见于急性发病，且多是10岁以下的幼儿。\n" +
                "\n" +
                "此外，患儿会有2-21天的潜伏期而且会有水痘、麻疹、上呼吸道感染等病毒感染史，儿童患上血小板减少性紫癜皮肤会有广泛的瘀斑、瘀点成片状，由于儿童的新陈代谢能力比较强，所以病程比较短一般为4-6周而且预后良好，颅内出血致死的现象比较少见。\n" +
                "\n" +
                "\n" +
                "育龄女性：\n" +
                "\n" +
                "与儿童血小板减少症不同的是，成年女性多见于慢性血小板减少症，且多发生于20岁-40岁之间的成年女性，患者起病比较缓慢，女性患者首要病症主要是月经量过多，而且病程较长。\n" +
                "\n" +
                "患者在出现外伤或小手术以后创口比较难愈合且出血不止，长期反复出血还会造成肝脾轻度肿大和贫血呈持续性或反复性发作，患者需要忍受疾病反复发作的折磨。\n" +
                "\n" +
                "血液病专家史主任介绍，虽然不同类型的血小板减少患者人群不同，但在疾病发生时，都要积极采取相应治疗措施，尤其是在发病初期，要积极治疗，切忌拖延病情，擅自用药。\n" +
                "\n\",\n" +
                "      \"learner\": 54540\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 20,\n" +
                "      \"name\": \"血小板减少长期吃激素，把“补钙”提上日程\",\n" +
                "      \"picSmall\": \"https://www.zyzpes.com/img.php?url=http://inews.gtimg.com/newsapp_match/0/4618846161/0\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/550a87da000168db06000338.jpg\",\n" +
                "      \"description\": \"血小板减少长期吃激素，把“补钙”提上日程\",\n" +
                "      \"content\": \"补钙?血小板减少了，为什么要补钙?很多ITP患者看着医生给开出的“钙剂”，都觉得莫名其妙。其实，对血小板减少患者来说，尤其是病情反复需长期口服激素维持血小板数值的患者，补钙很必要!\n" +
                "\n" +
                "\n" +
                "激素是西医治疗血小板减少的常规用药，用以快速提升血小板数值，尤其是血小板过低的病人，可以帮助他们避免严重的出血现象。\n" +
                "\n" +
                "这其中，一部分血小板减少病情通过用药，板值很快正常，并且不再反复，于是逐渐减药直至停药，板值依然正常，病情痊愈;而另一部分患者会比较容易出现吃药就升停药就降的现象，只能依靠一定剂量的药物维持血小板在比较安全的范围，防止板值过低，因此比较严重的出血现象。\n" +
                "\n" +
                "而随着用药剂量的增加，用药时间的延长，患者会表现出一定的不良反应，比如增重，肥胖，多毛，痤疮，还会引起骨质疏松，甚至是股骨头坏死现象。\n" +
                "\n" +
                "由此可见，长期服用激素类药物会对患者的骨骼造成影响，因此，建议长期服药的血小板减少病人要注意补钙。\n" +
                "\n" +
                "为什么糖皮质激素会导致骨质疏松，缺钙表现?\n" +
                "\n" +
                "糖皮质激素会促进体内钙、钾、磷的排泄，如果长期大剂量应用，体内这几种成分逐渐流失，可能诱发骨质疏松，严重时会发生骨折。\n" +
                "\n" +
                "如何补钙?\n" +
                "\n" +
                "长期服用激素类药物时，可采用药物和食物补钙相结合的方式。牛奶、虾皮、海带、豆腐等食物含钙丰富，可适当多吃。同时要少喝茶，尽量不吸烟，少喝酒和碳酸饮料。多做运动和晒太阳可促进钙的吸收。必要时，可遵医嘱口服钙剂。\n \",\n" +
                "      \"learner\": 45665\n" +
                "    }\n" +
                "  ],\n" +
                "  \"msg\": \"成功\"\n" +
                "}";

        allDataList = getDataFromJson(result);
        adapter = new ContentNewsAdapter(this, allDataList);
        newsList.setAdapter(adapter);
    }

    /**
     * 用于解析json数据
     * @param json 相应的json数据
     * @return 返回json数据中对应的list集合
     */
    private List<TestContentNewsBean> getDataFromJson(String json){
        List<TestContentNewsBean> dataList = new ArrayList<TestContentNewsBean>();
        JSONObject jsonNews = null;
        TestContentNewsBean newsBean = null;
        try {
            jsonNews = new JSONObject(json);
//            jsonNews = jsonNews.optJSONObject("result");
            JSONArray jsonArrayNews = jsonNews.optJSONArray("data");
            for (int s = 0; s < jsonArrayNews.length(); s++) {
                jsonNews = jsonArrayNews.optJSONObject(s);
                newsBean = new TestContentNewsBean();
                newsBean.setImageUrl(jsonNews.optString("picSmall"));

                newsBean.setTitle(jsonNews.optString("name"));
                newsBean.setContent(jsonNews.optString("content"));
                newsBean.setSrc(jsonNews.optString("description"));
                newsBean.setBrowseNumber(jsonNews.optInt("learner"));
//                newsBean.setTime(jsonNews.optString("time"));
//                newsBean.setContentUrl(jsonNews.optString("url"));
                dataList.add(newsBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
